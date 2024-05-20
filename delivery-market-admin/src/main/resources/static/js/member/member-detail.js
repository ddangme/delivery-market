const currentUrl = window.location.href;

$(document).ready(function () {
    allCheckBtn();
});




function changeMemberStatusColor(response, $td) {
    if (response === "정상") {
        $td.addClass('text-success');
    } else if (response === "탈퇴") {
        $td.addClass('text-danger')
    } else if (response === "정지") {
        $td.addClass('text-warning');
    }
}

function changeBenefitLevelColor(response, $td) {
    if (response === "VIP") {
        $td.addClass('text-primary');
    }
}