$(document).ready(function(){
    $(".clickable-row").click(function() {
        window.location = "/goods/" + $(this).attr("id");
    });
});