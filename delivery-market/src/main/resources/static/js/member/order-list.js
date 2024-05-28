$(document).ready(function () {
    getOrderList(0);
    addOrderHistoryBtnEvent();
});

function addOrderHistoryBtnEvent() {
    $('.orderHistory').click(function () {
        $('.border-black').removeClass('border-black');
        $(this).addClass('border-black');
        getOrderList($(this).attr('id'));
    });
}

function getOrderList(index) {
    $.get("/api/order/list/" + orderHistory[index], function (response) {
        console.log(response)
        displayOrderList(response);
    });
}

const orderHistory = [
    "THREE_MONTHS",
    "SIX_MONTHS",
    "ONE_YEAR",
    "THREE_YEARS"
];

function getFormatDate(response) {
    const date = new Date(response);
    return date.getFullYear() + "." +
        ('0' + (date.getMonth() + 1)).slice(-2) + "." +
        ('0' + date.getDate()).slice(-2) + " (" +
        ('0' + date.getHours()).slice(-2) + "시 " +
        ('0' + date.getMinutes()).slice(-2) + "분)";
}

const orderDiv = $(`<div class="my-5" style="cursor: pointer">
                    <div class="border-bottom hstack">
                        <p class="pb-2 createdAt mb-0 fw-bold">2024.05.17 (04시 42분)</p>
                        <p class="pb-2 ms-auto text-secondary">주문 상세 > </p>
                    </div>
                    <div class="row my-2">
                        <div class="col-2">
                            상품명
                        </div>
                        <div class="col-10 goodName">
                        </div>
                        <div class="col-2">
                            주문 번호
                        </div>
                        <div class="col-10 id">
                        </div>
                        <div class="col-2">
                            결제 금액
                        </div>
                        <div class="col-10">
                            <p class="mb-0">
                                <span class="price"></span>
                                <span>원</span>
                            </p>
                        </div>
                        <div class="col-2">
                            주문 상태
                        </div>
                        <div class="col-10 status">
                        </div>
                    </div>
                </div>`);

function displayOrderList(response) {
    $('#order-list').empty();
    response.forEach(function (order) {
        const area = orderDiv.clone();
        area.click(function () {
            location.href = "/my-page/order/" + order.id;
        });
        area.find('.goodName').text(order.goodName);
        area.find('.price').text(order.price.toLocaleString());
        area.find('.status').text(order.status);
        area.find('.createdAt').text(getFormatDate(order.createdAt));
        area.find('.id').text(order.id);

        $('#order-list').append(area);
    });
}