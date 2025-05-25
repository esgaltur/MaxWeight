// Ensure toWeek is always >= fromWeek
document.addEventListener('DOMContentLoaded', function() {
    const fromWeekSelect = document.getElementById('fromWeek');
    if (fromWeekSelect) {
        fromWeekSelect.addEventListener('change', function() {
            var fromWeek = parseInt(this.value);
            var toWeekSelect = document.getElementById('toWeek');
            var toWeek = parseInt(toWeekSelect.value);

            if (toWeek < fromWeek) {
                toWeekSelect.value = fromWeek;
            }
        });
    }
});