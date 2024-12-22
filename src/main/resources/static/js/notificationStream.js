document.addEventListener("DOMContentLoaded", () => {
    const eventSource = new EventSource(`/api/notifications/stream?userId=1`);  // Adjust userId dynamically if needed

    eventSource.onmessage = (event) => {
        const notification = JSON.parse(event.data);
        displayNotification(notification);
    };

    function displayNotification(notification) {
        const container = document.querySelector('.toast-container');

        const toast = document.createElement('div');
        toast.className = "toast align-items-center show";
        toast.setAttribute("role", "alert");
        toast.setAttribute("aria-live", "assertive");
        toast.setAttribute("aria-atomic", "true");
        toast.setAttribute("data-id", notification.notificationId);

        toast.innerHTML = `
            <div class="d-flex">
                <div class="toast-body">
                    ${notification.message}
                </div>
                <button type="button" class="btn-close me-2 m-auto" data-bs-dismiss="toast"></button>
            </div>
        `;

        container.appendChild(toast);

        // Auto-dismiss notification after 5 seconds
        setTimeout(() => {
            toast.classList.remove("show");
            toast.classList.add("hide");
        }, 5000);

        // Mark as seen when dismissed
        toast.querySelector('.btn-close').addEventListener('click', () => {
            fetch(`/api/notifications/mark-as-seen`, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify([notification.notificationId])
            });
        });
    }
});
