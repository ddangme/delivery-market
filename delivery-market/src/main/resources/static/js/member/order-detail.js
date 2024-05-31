const currentUrl = location.href;
const orderId = currentUrl.split('/').pop();
$(document).ready(function () {
    getOrderDetail();
    orderCancelBtnEvent();
});

function orderCancelBtnEvent() {
    $('#order-cancel-btn').click(function () {
        $.ajax({
            url: '/api/order/cancel/' + orderId,
            type: 'POST',
            success: function () {
                alert("구매가 취소되었습니다.");
                location.reload();
            },
            error: function (xhr) {
                alert(xhr.responseText);
            },
        })
    });
}

function getOrderDetail() {
    $.ajax({
        url: '/api/order/' + orderId,
        type: 'GET',
        success: function (response) {
            displayOrderDetail(response.order);
            displayPay(response.order);
            displayGoodList(response.goods)
            displayAddress(response.address);
        },
        error: function (xhr) {
            alert(xhr.responseText);
            window.history.back();
        }
    });
}

function displayAddress(address) {
    $('#recipientName').text(address.name);
    $('#recipientPhone').text(parsePhone(address.phone));
    $('#address').text(address.address);
}

function displayOrderDetail(order) {
    $('#deliveryStatus').text(order.deliveryStatus);
    $('#order-cancel-btn').prop('disabled', order.deliveryStatus !== "배송 준비중");
    $('.orderId').text(order.id);
    $('#memberName').text(order.memberName);
    $('#createdAt').text(getFormatDate(order.createdAt));
}


function displayGoodList(goods) {
    goods.forEach(function (good) {
        const div = goodDiv.clone();
        div.find('.name').text(good.name);
        displayImage(good.photo, div.find('.photo'));
        div.find('.good-photo-href').attr('href', '/goods/' + good.id);
        if (good.discountPrice === null) {
            div.find('.original-price').remove();
            div.find('.price').text(good.price.toLocaleString() + "원");
        } else {
            div.find('.price').text(good.discountPrice.toLocaleString() + "원");
            div.find('.original-price').text(good.price.toLocaleString() + "원");
        }
        div.find('.quantity').text(good.quantity + "개");
        if (good.reviewId === null) {
            div.find('.bg-success-subtle').remove();
        } else {
            div.find('.bg-primary-subtle').remove();
        }

        $('#good-list').append(div);
    });
}

const goodDiv = $(`<div class="hstack">
                            <div class="p-2">
                                <a class="good-photo-href">
                                    <img class="photo" style="width: 50px; height: 65px; cursor: pointer">
                                </a>
                            </div>
                            <div class="p-2">
                                <p class="m-0 name"></p>
                                <small class="m-0">
                                    <span class="price"></span>
                                    <span class="original-price text-body-tertiary text-decoration-line-through"></span>
                                    <span>|</span>
                                    <span class="quantity"></span>
                                </small>
                            </div>
                            <div class="p-2 ms-auto">
                                <a class="btn btn-sm bg-primary-subtle text-primary-emphasis">후기 작성</a>
                                <a class="btn btn-sm bg-success-subtle text-success-emphasis">후기 보기</a>
                            </div>
                        </div>`);

function displayImage(photo, $img) {
    const imageUrl = "/images/" + photo;

    $.get(imageUrl, function (data) {
        $img.attr('src', "data:" + "image/jpeg" + ";base64," + data);
    }).fail(function (xhr, status, error) {
        console.error("이미지를 받아오는 중 에러 발생:", status, error);
    });
}

function displayPay(order) {
    $('#totalPrice').text(order.totalPrice.toLocaleString() + "원");
    if (order.discountPrice !== undefined) {
        $('#discountPrice').text((order.totalPrice - order.discountPrice).toLocaleString() + "원");
        $('#payPrice').text((order.discountPrice + order.deliveryPrice).toLocaleString() + "원");
    } else {
        $('#payPrice').text((order.totalPrice + order.deliveryPrice).toLocaleString() + "원");
        $('#discountPrice').text(0 + "원");
    }
    $('#deliveryPrice').text(order.deliveryPrice.toLocaleString() + "원");
    $('#couponDiscountPrice').text(order.couponDiscountPrice.toLocaleString() + "원");
    $('#point').text(order.point.toLocaleString() + "원");
    $('#cash').text(order.cash.toLocaleString() + "원");
}


function getFormatDate(response) {
    const date = new Date(response);
    return date.getFullYear() + "." +
        ('0' + (date.getMonth() + 1)).slice(-2) + "." +
        ('0' + date.getDate()).slice(-2) + " " +
        ('0' + date.getHours()).slice(-2) + ":" +
        ('0' + date.getMinutes()).slice(-2) + ":" +
        ('0' + date.getSeconds()).slice(-2);
}

function parsePhone(phone) {
    return phone.replace(/(\d{3})(\d{4})/, "$1-$2-****");
}