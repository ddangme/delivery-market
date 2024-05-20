const currentUrl = window.location.href;
const trArea = $(`
<tr>
    <td class="id"></td>
    <td class="amount"></td>
    <td class="status"></td>
    <td class="memberName"></td>
    <td class="adminName"></td>
    <td class="requestAt"></td>
    <td class="responseAt"></td>
</tr>
`)

$(document).ready(function () {
    getCashChargingList();
});

function getCashChargingList() {
    $.ajax({
        url: '/api/members/cash' + currentUrl.substring(currentUrl.lastIndexOf('/list')),
        type: "GET",
        success: function (response) {
            console.log(response);
            addData(response.content);

            const pageInfo = {
                totalPages: response.totalPages,
                totalElements: response.totalElements,
                pageNumber: response.pageable.pageNumber,
                pageSize: response.pageable.pageSize,
                sort: response.pageable.sort
            }

            addPagination(pageInfo);
        },
        error: function (xhr) {
            alert(xhr.responseText);
            window.history;
        },
    })
}

function addPagination(pageInfo) {
    // 이전 페이지 링크 설정
    var previousPageLink = $('#pagination .page-item:first-child .page-link');
    if (pageInfo.pageNumber <= 0) {
        previousPageLink.addClass('disabled').attr('href', '#');
    } else {
        previousPageLink.removeClass('disabled').attr('href', '/members/cash/list?page=' + (pageInfo.pageNumber - 1));
    }

    // 페이지 번호 링크 설정
    $('#pagination .page-item:not(:first-child):not(:last-child)').remove(); // 이전 페이지 번호 제거
    for (var i = 0; i < pageInfo.totalPages; i++) {
        var pageNumber = i + 1;
        var pageItem = $('<li class="page-item"></li>');
        var pageLink = $('<a class="page-link" href="/members/cash/list?page=' + i + '">' + pageNumber + '</a>');
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
        nextPageLink.removeClass('disabled').attr('href', '/members/cash/list?page=' + (pageInfo.pageNumber + 1));
    }
}

function addData(content) {
    content.forEach(function (data) {
        let tr = trArea.clone();
        tr.find('.id').text(data.id);
        tr.find('.amount').text(data.amount.toLocaleString() + "원");
        tr.find('.status').text(data.status);
        changeFontColor(data.status, tr.find('.status'));
        tr.find('.memberName').text(data.memberName);
        tr.find('.adminName').text(data.adminName);
        tr.find('.requestAt').text(parseDate(data.requestAt));
        if (data.responseAt === null) {
            tr.find('.responseAt').text("-");
        } else {
            tr.find('.responseAt').text(parseDate(data.responseAt));
        }

        $('#cash-charging-list').append(tr);
    })
}

function changeFontColor(response, $td) {
    if (response === "요청") {
        $td.addClass('')
    } else if (response === "승낙") {
        $td.addClass('text-success');
    } else if (response === "거절") {
        $td.addClass('text-danger')
    } else if (response === "보류") {
        $td.addClass('text-warning');
    }
}

function parseDate(request) {
    const date = new Date(request);

    return date.getFullYear() + '년 ' +
        (date.getMonth() + 1) + '월 ' +
        date.getDate() + '일 ' +
        date.getHours().toString().padStart(2, '0') + ':' +
        date.getMinutes().toString().padStart(2, '0') + ':' +
        date.getSeconds().toString().padStart(2, '0');
}