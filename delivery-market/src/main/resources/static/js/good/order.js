$(document).ready(function () {
    $.get("/api/order/cart/list", function (response) {
        addGood(response);
    });
});

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