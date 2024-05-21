$(document).ready(function () {
    addAddressInModal();

    $.get("/api/order/info", function (response) {
        addGood(response.good);
        addAddress(response.address);
        addPay(response.pay);
        $('.use-point').text(response.usePoint.toLocaleString());
        $('.use-cash').text(response.useCash.toLocaleString());
        $('.total-price').text(response.totalGoodPrice.toLocaleString());
    });
});

function addPay(response) {
    const total = response.point + response.cash;
    $('.total-member-price').text(total.toLocaleString());
    $('.point').text(response.point.toLocaleString());
    $('.cash').text(response.cash.toLocaleString());
}

const addressModalArea = $(`
<div class="hstack gap-3 align-items-center mb-4">
    <div class="p-2">
        <input class="form-check-input p-3 rounded-circle check" type="checkbox">
    </div>
    <div class="p-2">
        <small class="mb-0 badge bg-warning-subtle text-black mb-2 main-address-badge">기본 배송지</small>
        <p class="mb-0 address-id-modal">
            <span class="road"></span>
            <span class="detail"></span>      
        </p>
        <small class="text-secondary">
            <span class="name"></span>
            <span>|</span>
            <span class="phone"></span>
        </small>
    </div>
</div>
`);

function addAddressInModal() {
    $('#address-modal-btn').click(function () {
        if (!$('#address-list-area').find('.align-items-center').length > 0) {
            $.get("/api/order/address/list", function (response) {
                response.forEach(function (item) {
                    console.log(item);
                    const address = addressModalArea.clone();
                    if (!item.main) {
                        address.find('.main-address-badge').remove();
                    }
                    address.find('.road').text(item.road);
                    address.find('.detail').text(item.detail);
                    address.find('.name').text(item.name);
                    address.find('.phone').text(item.phone.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'));
                    address.attr('id', item.id);

                    $('#address-list-area').append(address);
                    addCheckListener(address.find('.check'), item);
                });
            });
        }
    });
}

function addCheckListener($check, item) {
    $check.click(function () {
        $check.prop('checked', false);
        $('.main-badge').prop('hidden', !item.main)
        $('#name').text(item.name);
        $('#phone').text(item.phone);
        $('#road').text(item.road);
        $('#detail').text(item.detail);
        $('.address-id').attr('id', item.id);
        $('#modal-close-btn').click();
    });
}

const goodArea = $(`
<div class="row align-items-center my-3">
    <div class="col-2">
        <img class="good-photo" style="width: 67px; height: 87px">
    </div>
    <div class="col-6 text-wrap">
        <p class="mb-0 option-name"></p>
        <small class="text-secondary good-name"></small>
    </div>
    <div class="col-1 text-end">
        <span class="option-count"></span>
        <span>개</span>
    </div>
    <div class="col-3">
        <div class="vstack text-end">
            <p class="mb-0 good-price"></p>
            <p class="mb-0 good-discount-price"></p>
            <small class="text-decoration-line-through text-secondary good-original-price"></small>
        </div>
    </div>
</div>
`);

function addAddress(response) {
    $('#name').text(response.name);
    $('#phone').text(response.phone);
    $('#road').text(response.road);
    $('#detail').text(response.detail);
    $('.address-id').attr('id', response.id);
}

function addGood(response) {
    response.forEach(function (item) {
        const good = goodArea.clone();
        good.find('.option-name').text(item.optionName);
        good.find('.good-name').text(item.goodName);
        good.find('.option-count').text(item.optionCount);
        if (item.discountPrice === null) {
            good.find('.good-price').text(item.price.toLocaleString() + " 원");
            good.find('.good-discount-price').remove();
            good.find('.good-original-price').remove();
        } else {
            good.find('.good-price').remove();
            good.find('.good-discount-price').text(item.discountPrice.toLocaleString() + " 원");
            good.find('.good-original-price').text(item.price.toLocaleString() + " 원");
        }
        setImage(item.photo, good.find('.good-photo'));
        $('#good-area').append(good);
    });
}


function setImage(photo, $element) {
    $.get("/images/" + photo, function (data) {
        $element.attr('src', "data:image/jpeg;base64," + data);
    });
}