document.addEventListener("DOMContentLoaded", function() {
    // Function to fetch data from the server
    async function fetchData(date, userId) {
        console.log("Fetching data for date:", date);
        const response = await fetch(`/api/history/stove-durations?date=${date}&userId=${userId}`);
        if (!response.ok) {
            throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        console.log("Fetched data:", data);
        return data;
    }

    // Function to render the chart
    async function renderChart(date, userId) {
        try {
            const data = await fetchData(date, userId);
            if (!data || typeof data !== 'object') {
                throw new Error("Unexpected data format");
            }

            const labels = Object.keys(data);
            const values = Object.values(data);

            const ctx = document.getElementById('stoveDurationChart').getContext('2d');

            // Destroy existing chart if it exists
            if (window.stoveDurationChart instanceof Chart) {
                window.stoveDurationChart.destroy();
            }

            // Create the chart
            window.stoveDurationChart = new Chart(ctx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Stove On Duration',
                        data: values,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
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
                            title: { display: true, text: 'Duration' }
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
        renderChart(selectedDate, userId);
    });

    // Initialize the chart with today's date
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('datePicker').value = today;
    renderChart(today, userId);
});