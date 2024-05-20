const currentUrl = window.location.href;

const trArea = $(`
<tr>
    <td>
        <input class="form-check-input check" type="checkbox">
    </td>
    <td class="id"></td>
    <td class="name"></td>
    <td class="memberStatus"></td>
    <td class="benefitLevel"></td>
    <td class="phone"></td>
    <td class="cash"></td>
    <td class="point"></td>
</tr>
`);

$(document).ready(function () {
    getMemberList();
    allCheckBtn();
});

function getMemberList() {
    $.get('/api' + currentUrl.substring(currentUrl.lastIndexOf('/members')), function (response) {

        addData(response.content);

        const pageInfo = {
            totalPages: response.totalPages,
            totalElements: response.totalElements,
            pageNumber: response.pageable.pageNumber,
            pageSize: response.pageable.pageSize,
            sort: response.pageable.sort
        }
        addPagination(pageInfo);
    });
}

function addData(content) {
    content.forEach(function (data) {
        let tr = trArea.clone();
        tr.find('.id').text(data.id);
        tr.find('.name').text(data.name);
        tr.find('.memberStatus').text(data.memberStatus);
        changeMemberStatusColor(data.memberStatus, tr.find('.memberStatus'));
        tr.find('.benefitLevel').text(data.benefitLevel);
        changeBenefitLevelColor(data.benefitLevel, tr.find('.benefitLevel'));
        if (data.phone !== null) {
            tr.find('.phone').text(data.phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'));
        } else {
            tr.find('.phone').text("-");
        }
        tr.find('.cash').text(data.cash.toLocaleString() + " 원");
        tr.find('.point').text(data.point.toLocaleString() + " 원");
        tr.find('.check').change(function() {
            const allChecked = $('#member-list .check').length === $('#member-list .check:checked').length;
            $('.all-check').prop('checked', allChecked);
        });

        $('#member-list').append(tr);
    });
}

function getCheckedIds() {
    let ids = [];
    $('#member-list .check:checked').each(function () {
        const parent = $(this).closest('tr');
        const id = parent.find('.id').text();

        ids.push(id);
    });

    return ids;
}

function allCheckBtn() {
    $('.all-check').change(function() {
        const isChecked = $(this).is(':checked');
        $('#member-list .check').prop('checked', isChecked);
    });
}

function addPagination(pageInfo) {
    // 이전 페이지 링크 설정
    var previousPageLink = $('#pagination .page-item:first-child .page-link');
    if (pageInfo.pageNumber <= 0) {
        previousPageLink.addClass('disabled').attr('href', '#');
    } else {
        previousPageLink.removeClass('disabled').attr('href', '/members?page=' + (pageInfo.pageNumber - 1));
    }

    // 페이지 번호 링크 설정
    $('#pagination .page-item:not(:first-child):not(:last-child)').remove(); // 이전 페이지 번호 제거
    for (var i = 0; i < pageInfo.totalPages; i++) {
        var pageNumber = i + 1;
        var pageItem = $('<li class="page-item"></li>');
        var pageLink = $('<a class="page-link" href="/members/?page=' + i + '">' + pageNumber + '</a>');
        if (i === pageInfo.pageNumber) {
            pageLink.addClass('disabled');
        }
        pageItem.append(pageLink);
        $('#pagination .page-item:last-child').before(pageItem);
    }

    // 다음 페이지 링크 설정
    var nextPageLink = $('#pagination .page-item:last-child .page-link');
    if (pageInfo.pageNumber >= pageInfo.totalPages - 1) {
        nextPageLink.addClass('disabled').attr('href', '#');
    } else {
        nextPageLink.removeClass('disabled').attr('href', '/members?page=' + (pageInfo.pageNumber + 1));
    }
}

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