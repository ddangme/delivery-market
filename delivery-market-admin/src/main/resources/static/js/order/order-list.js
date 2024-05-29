$(document).ready(function () {
    addAllCheckEvent();
    getOrderList();
    filter();
    search();
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
        order.find('.price').text(item.price.toLocaleString() + "원");
        order.find('.status').text(item.status);
        order.find('.member').text(item.member);
        order.find('.createdAt').text(getFormatDate(item.createdAt));
        order.find('.check').change(function() {
            const allChecked = $('.check').length === $('.check:checked').length;
            $('.all-check').prop('checked', allChecked);
        });
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
        apiUrl = apiUrl.replace(/(page=)\d+/, '$1' + i);
    } else if (apiUrl.indexOf('?') !== -1) {
        apiUrl = apiUrl + '&page=' + i;
    } else {
        apiUrl = apiUrl + '?page=' + i;
    }

    return apiUrl;
}

const orderTr =
$(`<tr>
    <td><input class="form-check-input check" type="checkbox"></td>
    <td class="id"></td>
    <td class="price"></td>
    <td class="status"></td>
    <td class="member"></td>
    <td class="createdAt"></td>
</tr>
`);

function filter() {
    $("a[id$='-filter']").on("click", function(event) {
        const id = $(this).attr('id');
        const word = id.split('-filter')[0];
        if (currentUrl.includes('?')) {
            if (currentUrl.includes('status')) {
                location.href = currentUrl.replace(/(status=)[^&]*/, '$1' + word);
            } else {
                location.href = currentUrl + '&status=' + word;
            }
        } else {
            location.href = currentUrl + '?status=' + word;
        }
    });

}


function search() {
    $('#search-btn').click(function () {
        const keyword = $('#search-keyword').val();
        if (keyword === '') {
            return alert("주문자의 성명 혹은 주문번호를 입력해주세요.");
        }
        if (currentUrl.includes('?')) {
            if (currentUrl.includes('keyword')) {
                location.href = currentUrl.replace(/(keyword=)[^&]*/, '$1' + keyword);
            } else {
                location.href = currentUrl + '&keyword=' + keyword;
            }
        } else {
            location.href = currentUrl + '?keyword=' + keyword;
        }

    });
}

function getFormatDate(response) {
    const date = new Date(response);
    return date.getFullYear() + "." +
        ('0' + (date.getMonth() + 1)).slice(-2) + "." +
        ('0' + date.getDate()).slice(-2) + " (" +
        ('0' + date.getHours()).slice(-2) + "시 " +
        ('0' + date.getMinutes()).slice(-2) + "분)";
}