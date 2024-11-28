document.addEventListener("DOMContentLoaded", () => {
    // Motion Chart
    fetch('/api/motion-data')
        .then(response => response.json())
        .then(data => {
            const detectedCount = data.statuses.filter(status => status === 1).length;
            const noMotionCount = data.statuses.length - detectedCount;

            const motionGraphCtx = document.getElementById('motionGraph').getContext('2d');
            new Chart(motionGraphCtx, {
                type: 'pie',
                data: {
                    labels: ['Motion Detected', 'No Motion'],
                    datasets: [{
                        data: [detectedCount, noMotionCount],
                        backgroundColor: ['rgb(75, 192, 192)', 'rgb(255, 99, 132)']
                    }]
                },
                options: {
                    responsive: true,
                    aspectRatio: 3,
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        tooltip: {
                            callbacks: {
                                label: function(tooltipItem) {
                                    var label = tooltipItem.label || '';
                                    var value = tooltipItem.raw;
                                    var total = detectedCount + noMotionCount;
                                    var percentage = ((value / total) * 100).toFixed(2);
                                    return `${label}: ${value} (${percentage}%)`;
                                }
                            }
                        }
                    }
                }
            });
        });

    // Temperature Chart
    fetch('/api/temperature-data')
        .then(response => response.json())
        .then(data => {
            const temperatureGraphCtx = document.getElementById('temperatureGraph').getContext('2d');
            new Chart(temperatureGraphCtx, {
                type: 'line',
                data: {
                    labels: data.timestamps,
                    datasets: [{
                        label: 'Temperature (°C)',
                        data: data.values,
                        borderColor: 'rgb(255, 99, 132)',
                        backgroundColor: 'rgba(255, 99, 132, 0.2)',
                        fill: true
                    }]
                },
                options: {
                    responsive: true,
                    plugins: {
                        legend: {
                            position: 'top',
                        },
                        tooltip: {
                            callbacks: {
                                label: function(tooltipItem) {
                                    var label = tooltipItem.label || '';
                                    var value = tooltipItem.raw;
                                    return `${label}: ${value}°C`;
                                }
                            }
                        }
                    },
                    scales: {
                        x: {
                            ticks: {
                                display: false
                            },
                            title: {
                                display: true,
                                text: 'Time'
                            }
                        },
                        y: {
                            title: {
                                display: true,
                                text: 'Temperature (°C)'
                            },
                            ticks: {
                                beginAtZero: false
                            }
                        }
                    }
                }
            });
        });

    // Stove Usage Chart
    fetch('/api/stove-durations')
        .then(response => response.json())
        .then(data => {
            const labels = data.durations.map((_, index) => `Session ${index + 1}`);

            const stoveDurationCtx = document.getElementById('stoveDurationChart').getContext('2d');
            new Chart(stoveDurationCtx, {
                type: 'bar',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Stove Usage (Minutes)',
                        data: data.durations,
                        backgroundColor: 'rgba(75, 192, 192, 0.2)'
                    }]
                },
                options: {
                    responsive: true,
                    scales: {
                        y: {
                            beginAtZero: true,
                            title: {
                                display: true,
                                text: 'Duration (Minutes)'
                            }
                        },
                        x: {
                            ticks: {
                                display: false
                            },
                            title: {
                                display: true,
                                text: 'Sessions'
                            }
                        }
                    }
                }
            });
        })
        .catch(error => {
            console.error("Error fetching stove durations:", error);
        });
});
