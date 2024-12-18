document.addEventListener('DOMContentLoaded', function() {
    const loadPredictionBtn = document.getElementById('loadPredictionBtn');
    const predictionMessage = document.getElementById('prediction-message');

    loadPredictionBtn.addEventListener('click', function() {
        loadPredictionBtn.textContent = 'Loading...';
        loadPredictionBtn.disabled = true;

        // Fetch the prediction data from the /prediction-data endpoint
        fetch('/prediction-data')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();  // Parse the JSON response
            })
            .then(data => {
                // Check if 'future_prediction' is null
                if (data && data.future_prediction !== null) {
                    // If future_prediction exists, display it
                    const prediction = data.future_prediction;
                    predictionMessage.textContent = `Prediction: ${prediction.temperature}°C, Timeframe: ${prediction.timeframe}`;
                } else {
                    // If future_prediction is null, show a custom message
                    predictionMessage.textContent = 'No dangerous situation detected in the next 2 hours.';
                }
            })
            .catch(error => {
                predictionMessage.textContent = 'Error loading prediction.';
                console.error('Error fetching prediction:', error);
            })
            .finally(() => {
                loadPredictionBtn.textContent = 'Load Prediction';
                loadPredictionBtn.disabled = false;
            });
    });
});
