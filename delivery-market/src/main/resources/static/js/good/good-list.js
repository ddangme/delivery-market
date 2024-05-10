$(document).ready(function(){
    $('.border-light').click(function(){
        var goodId = $(this).attr('id');
        window.location.href = "/goods/" + goodId;
    });
});