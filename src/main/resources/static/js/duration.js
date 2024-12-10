document.addEventListener("DOMContentLoaded", function() {
    // Function to fetch data from the server
    async function fetchData(date, userId, endpoint, chartType) {
        const url = chartType === 'daily'
            ? `${endpoint}?date=${date}&userId=${userId}`
            : `${endpoint}?month=${new Date(date).getMonth() + 1}&year=${new Date(date).getFullYear()}&userId=${userId}`;

        console.log("Fetching data from:", url);
        const response = await fetch(url);
        if (!response.ok) {
            throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        console.log("Fetched data:", data);
        return data;
    }

    // Function to render the chart
    async function renderChart(date, userId, chartType = 'daily') {
        try {
            const endpoint = chartType === 'daily' ? '/api/history/stove-durations' : '/api/history/monthly-stove-durations';
            const data = await fetchData(date, userId, endpoint, chartType);
            if (!data || typeof data !== 'object') {
                throw new Error("Unexpected data format");
            }

            const labels = Object.keys(data);
            const values = Object.values(data);

            const ctx = document.getElementById(`${chartType}StoveDurationChart`).getContext('2d');

            // Destroy existing chart if it exists
            if (window[`${chartType}StoveDurationChart`] instanceof Chart) {
                window[`${chartType}StoveDurationChart`].destroy();
            }

            // Create the chart
            window[`${chartType}StoveDurationChart`] = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: chartType === 'daily' ? 'Stove On Duration' : 'Average Stove Usage',
                        data: values,
                        backgroundColor: chartType === 'daily' ? 'rgba(255, 99, 132, 0.2)' : 'rgba(54, 162, 235, 0.2)',
                        borderColor: chartType === 'daily' ? 'rgba(255, 99, 132, 1)' : 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        x: {
                            title: { display: true, text: 'Hour' }
                        },
                        y: {
                            beginAtZero: true,
                            title: { display: true, text: chartType === 'daily' ? 'Duration' : 'Average Usage' }
                        }
                    }
                }
            });

            console.log("Chart rendered successfully.");
        } catch (error) {
            console.error("Error rendering chart:", error);
            alert("Failed to load data for the selected date.");
        }
    }

    // Add an event listener for the date picker
    document.getElementById('datePicker').addEventListener('change', (event) => {
        const selectedDate = event.target.value;
        renderChart(selectedDate, userId, 'daily');
        renderChart(selectedDate, userId, 'monthly');
    });

    // Initialize the charts with today's date
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('datePicker').value = today;
    renderChart(today, userId, 'daily');
    renderChart(today, userId, 'monthly');
});