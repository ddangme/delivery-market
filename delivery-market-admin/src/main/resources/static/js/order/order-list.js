$(document).ready(function () {
    addAllCheckEvent();
    getOrderList();
    filter();
});

const currentUrl = window.location.href;

function addAllCheckEvent() {
    $('.all-check').on('click', function() {
        $('.form-check-input').prop('checked', $('.all-check').prop('checked'));
    });
}

function getOrderList() {
    const apiUrl = currentUrl.replace('/orders', '/api/orders');
    $.get(apiUrl, function (response) {
        printList(response)
    })
}

function printList(response) {
    response.content.forEach(function (item) {
        const order = orderTr.clone();
        order.find('.id').text(item.id);
        order.find('.name').text(item.name);
        order.find('.price').text(item.price.toLocaleString() + "원");
        order.find('.status').text(item.status);
        order.find('.member').text(item.member);
        $('#order-list').append(order);
    });

    const pageInfo = {
        totalPages: response.totalPages,
        totalElements: response.totalElements,
        pageNumber: response.pageable.pageNumber,
        pageSize: response.pageable.pageSize,
        sort: response.pageable.sort
    }
    addPagination(pageInfo);
}
function addPagination(pageInfo) {
    var previousPageLink = $('#pagination .page-item:first-child .page-link');
    if (pageInfo.pageNumber <= 0) {
        previousPageLink.addClass('disabled').attr('href', getPage(pageInfo.pageNumber - 1));
    } else {
        previousPageLink.removeClass('disabled').attr('href', getPage(pageInfo.pageNumber - 1));
    }

    // 페이지 번호 링크 설정
    $('#pagination .page-item:not(:first-child):not(:last-child)').remove(); // 이전 페이지 번호 제거
    for (var i = 0; i < pageInfo.totalPages; i++) {
        var pageNumber = i + 1;
        var pageItem = $('<li class="page-item"></li>');
        var pageLink = $('<a class="page-link" href="' + getPage(i) + '">' + pageNumber + '</a>');
        if (i === pageInfo.pageNumber) {
            pageLink.addClass('disabled');
        }
        pageItem.append(pageLink);
        $('#pagination .page-item:last-child').before(pageItem);
    }

    // 다음 페이지 링크 설정
    var nextPageLink = $('#pagination .page-item:last-child .page-link');
    if (pageInfo.pageNumber >= pageInfo.totalPages - 1) {
        nextPageLink.addClass('disabled').attr('href', getPage(pageInfo.pageNumber + 1));
    } else {
        nextPageLink.removeClass('disabled').attr('href', getPage(pageInfo.pageNumber + 1));
    }
}

function getPage(i) {
    let apiUrl = currentUrl;
    if (apiUrl.match(/page=\d+/)) {
        // page= 뒤의 숫자를 변경
        apiUrl = apiUrl.replace(/(page=)\d+/, '$1' + i);
    } else if (apiUrl.indexOf('?') !== -1) {
        // page=가 없고, ?가 있는 경우
        apiUrl = apiUrl + '&page=' + i;
    } else {
        // page=가 없고, ?가 없는 경우
        apiUrl = apiUrl + '?page=' + i;
    }

    return apiUrl;
}

const orderTr =
$(`<tr>
    <td><input class="form-check-input" type="checkbox"></td>
    <td class="id"></td>
    <td class="name"></td>
    <td class="price"></td>
    <td class="status"></td>
    <td class="member"></td>
</tr>
`);

function filter() {
    $("a[id$='-filter']").on("click", function(event) {
        const id = $(this).attr('id');
        const word = id.split('-filter')[0];
        if (currentUrl.indexOf('?') !== -1) {
            location.href = currentUrl + '&status=' + word;
        } else {
            location.href = currentUrl + '?status=' + word;
        }
    });

}
