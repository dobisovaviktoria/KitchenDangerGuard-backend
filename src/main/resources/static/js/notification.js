document.addEventListener("DOMContentLoaded", () => {
    const datePicker = document.getElementById("datePicker");
    const hourlyChartCanvas = document.getElementById("hourlyNotificationChart");

    // Initialize the chart variable
    let hourlyChart;

    // Function to fetch data from the API
    async function fetchHourlyNotifications(date) {
        try {
            const response = await fetch(`/api/notifications/hourly?date=${date}`);
            if (!response.ok) {
                throw new Error(`Failed to fetch data: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching notification data:", error);
            throw error;
        }
    }

    // Function to render the chart
    async function renderHourlyChart(date) {
        try {
            const data = await fetchHourlyNotifications(date);

            const labels = Object.keys(data); // Hours (e.g., "00:00", "01:00", etc.)
            const counts = Object.values(data); // Notification counts for each hour

            // Destroy the existing chart instance if it exists
            if (hourlyChart) {
                hourlyChart.destroy();
            }

            // Create a new chart instance for a line graph
            hourlyChart = new Chart(hourlyChartCanvas, {
                type: "line",
                data: {
                    labels: labels,
                    datasets: [
                        {
                            label: "Notifications Per Hour",
                            data: counts,
                            borderColor: "rgba(75, 192, 192, 1)",
                            backgroundColor: "rgba(75, 192, 192, 0.2)",
                            tension: 0.4, // Adds smooth curves
                            pointRadius: 4,
                            pointBackgroundColor: "rgba(75, 192, 192, 1)",
                        },
                    ],
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            display: true,
                        },
                    },
                    scales: {
                        x: {
                            title: {
                                display: true,
                                text: "Hour",
                            },
                        },
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: "Notification Count",
                            },
                        },
                    },
                },
            });
        } catch (error) {
            console.error("Error rendering chart:", error);
        }
    }

    // Event listener for the date picker
    datePicker.addEventListener("change", (event) => {
        const selectedDate = event.target.value;
        if (selectedDate) {
            renderHourlyChart(selectedDate);
        }
    });

    // Default chart rendering for today's date
    const today = new Date().toISOString().split("T")[0];
    datePicker.value = today;
    renderHourlyChart(today);
});
