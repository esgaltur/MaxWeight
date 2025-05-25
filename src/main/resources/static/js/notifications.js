// Notification system
document.addEventListener('DOMContentLoaded', function() {
    // Create notification container if it doesn't exist
    let notificationContainer = document.querySelector('.notification-container');
    if (!notificationContainer) {
        notificationContainer = document.createElement('div');
        notificationContainer.className = 'notification-container position-fixed top-0 end-0 p-3';
        notificationContainer.style.zIndex = '1050';
        document.body.appendChild(notificationContainer);
    }

    // Connect to SSE endpoint
    const eventSource = new EventSource('/api/notifications/subscribe');

    // Handle connection open
    eventSource.onopen = function() {
        console.log('SSE connection established');
    };

    // Handle PING events
    eventSource.addEventListener('PING', function(e) {
        console.log('Received PING:', e.data);
    });

    // Handle PROGRAM_GENERATED events
    eventSource.addEventListener('PROGRAM_GENERATED', function(e) {
        console.log('Program generated notification received:', e.data);

        // Parse the notification data
        const data = JSON.parse(e.data);

        // Create message text
        let message = `Someone generated ${data.programsCount} training program(s) for weeks ${data.fromWeek}-${data.toWeek} with max weight ${data.maxWeight} kg.`;

        // Add trace ID if available
        if (data.traceId) {
            message += `<br><small class="text-muted">Trace ID: ${data.traceId}</small>`;
        }

        // Create and show the notification
        showNotification('Program Generated', message);
    });

    // Handle errors
    eventSource.onerror = function(e) {
        console.error('SSE connection error:', e);
        eventSource.close();

        // Try to reconnect after 5 seconds
        setTimeout(function() {
            console.log('Attempting to reconnect to SSE...');
            location.reload();
        }, 5000);
    };

    // Function to show a notification
    function showNotification(title, message) {
        // Create toast element
        const toastId = 'toast-' + Date.now();
        const toast = document.createElement('div');
        toast.className = 'toast show';
        toast.setAttribute('role', 'alert');
        toast.setAttribute('aria-live', 'assertive');
        toast.setAttribute('aria-atomic', 'true');
        toast.id = toastId;

        // Set toast content
        toast.innerHTML = `
            <div class="toast-header bg-primary text-white">
                <strong class="me-auto">${title}</strong>
                <small>Just now</small>
                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="toast" aria-label="Close"></button>
            </div>
            <div class="toast-body">
                ${message}
            </div>
        `;

        // Add toast to container
        notificationContainer.appendChild(toast);

        // Initialize Bootstrap toast
        const bsToast = new bootstrap.Toast(toast);
        bsToast.show();

        // Remove toast after it's hidden
        toast.addEventListener('hidden.bs.toast', function() {
            notificationContainer.removeChild(toast);
        });

        // Auto-hide after 5 seconds
        setTimeout(function() {
            bsToast.hide();
        }, 5000);
    }
});
