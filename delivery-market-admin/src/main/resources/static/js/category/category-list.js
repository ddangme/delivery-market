$(document).ready(function() {
    $(".clickable-row").click(function() {
        window.location = "/categories/" + $(this).attr("id");
    });
});