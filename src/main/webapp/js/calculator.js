/**
 * Calculator App - Client-side JavaScript
 * Enhances UX with keyboard shortcuts and auto-select.
 */
(function () {
    'use strict';

    // Auto-select the first radio that was previously selected
    document.addEventListener('DOMContentLoaded', function () {
        const radios = document.querySelectorAll('input[name="operator"]');
        const firstChecked = Array.from(radios).find(r => r.checked);
        if (!firstChecked && radios.length > 0) {
            // Default to + on fresh load
            radios[0].checked = true;
        }

        // Keyboard shortcut: Enter to submit
        document.addEventListener('keydown', function (e) {
            if (e.key === 'Enter' && document.activeElement.tagName !== 'BUTTON') {
                const form = document.getElementById('calcForm');
                if (form) form.submit();
            }
        });

        // Number pad keyboard shortcuts for operators
        const operatorMap = {
            '+': '+', '-': '-', '*': '*', '/': '/'
        };
        document.addEventListener('keydown', function (e) {
            if (document.activeElement.type === 'number') return;
            const op = operatorMap[e.key];
            if (op) {
                const radio = document.querySelector(`input[name="operator"][value="${op}"]`);
                if (radio) radio.checked = true;
            }
        });

        // Focus first input on load
        const firstInput = document.getElementById('operand1');
        if (firstInput) firstInput.focus();
    });
})();
