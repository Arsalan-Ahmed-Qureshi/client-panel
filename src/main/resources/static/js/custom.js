// Custom JavaScript for Client Panel

document.addEventListener('DOMContentLoaded', function() {
    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert:not(.alert-permanent)');
    alerts.forEach(alert => {
        setTimeout(() => {
            const bsAlert = new bootstrap.Alert(alert);
            // Don't auto-dismiss, keep them visible for user interaction
        }, 5000);
    });

    // Form validation
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function(event) {
            if (!form.checkValidity() === false) {
                event.preventDefault();
                event.stopPropagation();
            }
            form.classList.add('was-validated');
        });
    });

    // Confirm delete action
    const deleteButtons = document.querySelectorAll('button[type="submit"][class*="btn-danger"]');
    deleteButtons.forEach(button => {
        if (!button.hasAttribute('onclick')) {
            button.addEventListener('click', function(event) {
                if (!confirm('Are you sure you want to delete this client?')) {
                    event.preventDefault();
                }
            });
        }
    });

    // Phone number input formatter (optional)
    const mobileInput = document.getElementById('mobile');
    if (mobileInput) {
        mobileInput.addEventListener('input', function(e) {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    }

    const phoneIdInput = document.getElementById('phoneNumberId');
    if (phoneIdInput) {
        phoneIdInput.addEventListener('input', function(e) {
            this.value = this.value.replace(/[^0-9]/g, '');
        });
    }

    const clientIdInput = document.getElementById('clientId');
    if (clientIdInput) {
        clientIdInput.addEventListener('input', function(e) {
            this.value = this.value.replace(/[^a-zA-Z0-9_-]/g, '');
        });
    }
});

// Utility function to show loading state on buttons
function setButtonLoading(buttonElement, isLoading = true) {
    if (isLoading) {
        buttonElement.disabled = true;
        buttonElement.innerHTML = '<span class="spinner-border spinner-border-sm me-2" role="status" aria-hidden="true"></span>Loading...';
    } else {
        buttonElement.disabled = false;
        buttonElement.innerHTML = buttonElement.dataset.originalText;
    }
}

// Utility function for AJAX calls (if needed in future)
function makeApiCall(url, method = 'GET', data = null) {
    const options = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'X-Requested-With': 'XMLHttpRequest'
        }
    };

    if (data && (method === 'POST' || method === 'PUT' || method === 'PATCH')) {
        options.body = JSON.stringify(data);
    }

    return fetch(url, options);
}
