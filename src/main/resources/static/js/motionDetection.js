document.addEventListener("DOMContentLoaded", function () {
    async function fetchMotionData(date, userId) {
        const url = `/api/history/motion-data?date=${date}&userId=${userId}`;

        try {
            console.log("Fetching motion data from:", url);
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error("Failed to fetch motion data");
            }
            const data = await response.json();
            console.log("Fetched motion data:", data);

            const {temperature_ranges, motion_counts} = data;

            const chartData = {
                labels: temperature_ranges,
                datasets: [
                    {
                        label: 'Motion Detected',
                        data: motion_counts.motion,
                        backgroundColor: 'rgba(54, 162, 235, 0.2)',
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1
                    },
                    {
                        label: 'No Motion',
                        data: motion_counts.no_motion,
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        borderColor: 'rgba(255, 99, 132, 1)',
                        borderWidth: 1
                    }
                ]
            };

            const ctx = document.getElementById('motionDetectionChart').getContext('2d');

            // Destroy existing chart if it exists
            if (window.motionDetectionChart instanceof Chart) {
                window.motionDetectionChart.destroy();
            }

            // Create the chart
            window.motionDetectionChart = new Chart(ctx, {
                type: 'bar',
                data: chartData,
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            display: true,
                        },
                    },
                    scales: {
                        x: {
                            title: {display: true, text: 'Temperature Range (°C)'}
                        },
                        y: {
                            title: {display: true, text: 'Motion Count'},
                            beginAtZero: true
                        }
                    }
                }
            });
        } catch (error) {
            console.error("Error fetching motion detection data:", error);
            alert("Failed to load motion data for the selected date.");
        }
    }

    // Add an event listener for the date picker
    document.getElementById('datePicker').addEventListener('change', (event) => {
        const selectedDate = event.target.value;
        fetchMotionData(selectedDate, userId);
    });

    // Initialize the motion graph with today's date
    const today = new Date().toISOString().split('T')[0];
    document.getElementById('datePicker').value = today;
    fetchMotionData(today, userId);
});