$(document).ready(function(){
    $('.card').click(function(){
        var goodId = $(this).attr('id');
        window.location.href = "/goods/" + goodId;
    });
});