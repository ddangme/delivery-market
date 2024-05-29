$(document).ready(function () {
    addAllCheckEvent();
    getOrderList();
});

const currentUrl = window.location.href;

function addAllCheckEvent() {
    $('.all-check').on('click', function() {
        $('.form-check-input').prop('checked', $('.all-check').prop('checked'));
    });
}

function getOrderList() {
    $.get('/api' + currentUrl.substring(currentUrl.lastIndexOf('/orders')), function (response) {
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
    })
}
function addPagination(pageInfo) {
    // 이전 페이지 링크 설정
    var previousPageLink = $('#pagination .page-item:first-child .page-link');
    if (pageInfo.pageNumber <= 0) {
        previousPageLink.addClass('disabled').attr('href', '#');
    } else {
        previousPageLink.removeClass('disabled').attr('href', '/orders?page=' + (pageInfo.pageNumber - 1));
    }

    // 페이지 번호 링크 설정
    $('#pagination .page-item:not(:first-child):not(:last-child)').remove(); // 이전 페이지 번호 제거
    for (var i = 0; i < pageInfo.totalPages; i++) {
        var pageNumber = i + 1;
        var pageItem = $('<li class="page-item"></li>');
        var pageLink = $('<a class="page-link" href="/orders/?page=' + i + '">' + pageNumber + '</a>');
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
        nextPageLink.removeClass('disabled').attr('href', '/orders?page=' + (pageInfo.pageNumber + 1));
    }
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