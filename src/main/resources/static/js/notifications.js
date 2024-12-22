document.addEventListener("DOMContentLoaded", () => {
    const toastElements = document.querySelectorAll(".toast");

    toastElements.forEach(toastEl => {
        const notificationId = toastEl.getAttribute("data-id"); // Ensure this matches the backend ID
        const dismissButton = toastEl.querySelector(".btn-close");

        dismissButton.addEventListener("click", () => {
            // POST request to mark the notification as seen
            fetch(`/api/notifications/mark-as-seen`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify([notificationId]) // Send the notification ID as an array
            })
                .then(response => {
                    if (!response.ok) {
                        console.error("Failed to mark notification as seen.");
                    } else {
                        console.log(`Notification ${notificationId} marked as seen.`);
                    }
                })
                .catch(err => console.error("Error while marking notification as seen:", err));
        });
    });
});
