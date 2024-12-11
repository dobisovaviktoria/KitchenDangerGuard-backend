document.addEventListener("DOMContentLoaded", () => {
    const datePicker = document.getElementById("datePicker");
    const weeklyChartCanvas = document.getElementById("weeklyNotificationChart");

    let weeklyChart; // Variable to hold the chart instance

    // Function to fetch weekly notifications
    async function fetchWeeklyNotifications(userId, date) {
        try {
            const response = await fetch(`/api/notifications/weekly?userId=${userId}&date=${date}`);
            if (!response.ok) {
                throw new Error(`Failed to fetch data: ${response.statusText}`);
            }
            return await response.json();
        } catch (error) {
            console.error("Error fetching weekly notification data:", error);
            throw error;
        }
    }

    // Function to render the weekly chart
    async function renderWeeklyChart(userId, date) {
        try {
            const data = await fetchWeeklyNotifications(userId, date);

            // Extract labels (days) and data (counts)
            const labels = Object.keys(data); // Days of the week
            const counts = Object.values(data); // Notification counts

            // Destroy the existing chart instance if it exists
            if (weeklyChart) {
                weeklyChart.destroy();
            }

            // Create a new chart instance
            weeklyChart = new Chart(weeklyChartCanvas, {
                type: "line",
                data: {
                    labels: labels,datasets: [
                        {
                            label: "Notifications Per Day",
                            data: counts,
                            borderColor: "rgba(54, 162, 235, 1)", // Vibrant blue for the line
                            backgroundColor: "rgba(54, 162, 235, 0.2)", // Light translucent blue fill
                            borderWidth: 2, // Thicker line for better visibility
                            tension: 0.3, // Smooth curves, slightly sharper than 0.4
                            pointRadius: 5, // Larger points for emphasis
                            pointBackgroundColor: "rgba(54, 162, 235, 1)", // Matching blue for points
                            pointBorderColor: "rgba(255, 99, 132, 1)", // Contrasting pink border for points
                            pointBorderWidth: 2, // Thicker point border for emphasis
                            hoverBackgroundColor: "rgba(255, 206, 86, 1)", // Bright yellow on hover
                            hoverRadius: 8, // Larger points on hover for interactivity
                            fill: true, // Fill the area below the curve
                            gradient: { // Hypothetical feature for gradient support; adjust if your library supports gradients
                                start: "rgba(54, 162, 235, 0.2)",
                                end: "rgba(255, 206, 86, 0.2)",
                            },
                        },
                    ]

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
                                text: "Day of the Week",
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
            console.error("Error rendering weekly chart:", error);
        }
    }

    // Event listener for the date picker
    datePicker.addEventListener("change", () => {
        const selectedDate = datePicker.value;

            renderWeeklyChart(userId, selectedDate);

    });

    // Default chart rendering for today's date
    const today = new Date().toISOString().split("T")[0];
    datePicker.value = today;

    renderWeeklyChart(userId, today);
});
