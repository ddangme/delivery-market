$(document).ready(function () {
    $.get("/api/navbar/login-check", function (data) {
        if (data !== "") {
            $('#login-user-name').text(data + " 님");
            $('#logout-status-area').css('display', 'none');
            $('#login-status-area').css('display', 'block');
            $.get("/api/navbar/cart-count", function (data) {
                if (data !== 0) {
                    $('.cart-badge').text(data);
                } else {
                    $('.cart-badge').remove();
                }
            });
        } else {
            $('#login-status-area').css('display', 'none');
            $('#logout-status-area').css('display', 'block');
        }
    });
});