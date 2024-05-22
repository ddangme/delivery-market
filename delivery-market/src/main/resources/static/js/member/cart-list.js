const goodDiv = $(`
                <div class="row align-items-center my-3">
                    <div class="col-2 hstack gap-3">
                        <input class="form-check-input" type="checkbox" value="">
                        <a class="good-photo-href">
                            <img class="good-photo" style="width: 67px; height: 87px; cursor: pointer">
                        </a>
                    </div>
                    <div class="col-4 text-wrap">
                        <p class="mb-0 option-name"></p>
                        <small class="good-name" style="color: gray"></small>
                    </div>
                    <div class="col-3">
                        <div class="btn-group me-2 ms-0" role="group" aria-label="First group">
                            <button type="button" class="btn btn-outline-secondary minus-btn">-</button>
                            <input type="text" class="form-control option-quantity">
                            <button type="button" class="btn btn-outline-secondary plus-btn">+</button>
                        </div>
                    </div>
                    <div class="col-2">
                        <div class="vstack text-end">
                            <p class="mb-0 good-price"></p>
                            <p class="mb-0 good-discount-price"></p>
                            <p class="text-decoration-line-through good-original-price" style="color: gray"></p>
                        </div>
                    </div>
                    <div class="col-1">
                        <button type="button" class="btn-close" aria-label="Close"></button>
                    </div>
                </div>`);

const listDiv = $(`<div class="vstack gap-3">
                <div class="hstack">
                    <div class="p-2">
                        <input class="form-check-input" type="checkbox" value="" id="all-check-top">
                        <label class="form-check-label" for="all-check-top">
                            전체 선택
                            <span>(</span>
                            <span class="check-count" id="check-count"></span>
                            <span>/</span>
                            <span class="sale-status-count" id="sale-status-count"></span>
                            <span>)</span>
                        </label>
                    </div>
                    <div class="p-2 ms-auto">
                        <span class="check-delete" style="cursor: pointer">선택 삭제</span>
                    </div>
                </div>
                <div class="border-bottom my-0"></div>
                <div class="accordion">
                    <div class="accordion-item" id="refrigerated-div">
                        <h2 class="accordion-header">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#refrigerated-list" aria-expanded="false" aria-controls="goods-option-input-area" style="background:white">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-thermometer-low" viewBox="0 0 16 16">
                                    <path d="M9.5 12.5a1.5 1.5 0 1 1-2-1.415V9.5a.5.5 0 0 1 1 0v1.585a1.5 1.5 0 0 1 1 1.415"/>
                                    <path d="M5.5 2.5a2.5 2.5 0 0 1 5 0v7.55a3.5 3.5 0 1 1-5 0zM8 1a1.5 1.5 0 0 0-1.5 1.5v7.987l-.167.15a2.5 2.5 0 1 0 3.333 0l-.166-.15V2.5A1.5 1.5 0 0 0 8 1"/>
                                </svg>
                                냉장 상품
                            </button>
                        </h2>
                        <div class="accordion-collapse collapse show">
                            <div class="accordion-body py-3" id="refrigerated-list">
                                
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item" id="frozen-div">
                        <h2 class="accordion-header">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#frozen-list" aria-expanded="false" aria-controls="goods-option-input-area" style="background:white">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-thermometer-snow" viewBox="0 0 16 16">
                                    <path d="M5 12.5a1.5 1.5 0 1 1-2-1.415V9.5a.5.5 0 0 1 1 0v1.585A1.5 1.5 0 0 1 5 12.5"/>
                                    <path d="M1 2.5a2.5 2.5 0 0 1 5 0v7.55a3.5 3.5 0 1 1-5 0zM3.5 1A1.5 1.5 0 0 0 2 2.5v7.987l-.167.15a2.5 2.5 0 1 0 3.333 0L5 10.486V2.5A1.5 1.5 0 0 0 3.5 1m5 1a.5.5 0 0 1 .5.5v1.293l.646-.647a.5.5 0 0 1 .708.708L9 5.207v1.927l1.669-.963.495-1.85a.5.5 0 1 1 .966.26l-.237.882 1.12-.646a.5.5 0 0 1 .5.866l-1.12.646.884.237a.5.5 0 1 1-.26.966l-1.848-.495L9.5 8l1.669.963 1.849-.495a.5.5 0 1 1 .258.966l-.883.237 1.12.646a.5.5 0 0 1-.5.866l-1.12-.646.237.883a.5.5 0 1 1-.966.258L10.67 9.83 9 8.866v1.927l1.354 1.353a.5.5 0 0 1-.708.708L9 12.207V13.5a.5.5 0 0 1-1 0v-11a.5.5 0 0 1 .5-.5"/>
                                </svg>
                                냉동 상품
                            </button>
                        </h2>
                        <div class="accordion-collapse collapse show">
                            <div class="accordion-body py-3" id="frozen-list">
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item" id="room-temperature-div">
                        <h2 class="accordion-header">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#room-temperature-list" aria-expanded="false" aria-controls="goods-option-input-area" style="background:white">
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-thermometer-sun" viewBox="0 0 16 16">
                                    <path d="M5 12.5a1.5 1.5 0 1 1-2-1.415V2.5a.5.5 0 0 1 1 0v8.585A1.5 1.5 0 0 1 5 12.5"/>
                                    <path d="M1 2.5a2.5 2.5 0 0 1 5 0v7.55a3.5 3.5 0 1 1-5 0zM3.5 1A1.5 1.5 0 0 0 2 2.5v7.987l-.167.15a2.5 2.5 0 1 0 3.333 0L5 10.486V2.5A1.5 1.5 0 0 0 3.5 1m5 1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-1 0v-1a.5.5 0 0 1 .5-.5m4.243 1.757a.5.5 0 0 1 0 .707l-.707.708a.5.5 0 1 1-.708-.708l.708-.707a.5.5 0 0 1 .707 0M8 5.5a.5.5 0 0 1 .5-.5 3 3 0 1 1 0 6 .5.5 0 0 1 0-1 2 2 0 0 0 0-4 .5.5 0 0 1-.5-.5M12.5 8a.5.5 0 0 1 .5-.5h1a.5.5 0 1 1 0 1h-1a.5.5 0 0 1-.5-.5m-1.172 2.828a.5.5 0 0 1 .708 0l.707.708a.5.5 0 0 1-.707.707l-.708-.707a.5.5 0 0 1 0-.708M8.5 12a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-1 0v-1a.5.5 0 0 1 .5-.5"/>
                                </svg>
                                실온 상품
                            </button>
                        </h2>
                        <div class="accordion-collapse collapse show">
                            <div class="accordion-body py-3" id="room-temperature-list">
                            </div>
                        </div>
                    </div>
                    <div class="accordion-item" id="stop-div">
                        <h2 class="accordion-header">
                            <button class="accordion-button" type="button" data-bs-toggle="collapse"
                                    data-bs-target="#stop-list" aria-expanded="false" aria-controls="goods-option-input-area" style="background:white">
                                구매불가 상품
                            </button>
                        </h2>
                        <div class="accordion-collapse collapse show">
                            <div class="accordion-body py-3" id="stop-list">
                            </div>
                        </div>
                    </div>
                </div>
                <div class="hstack">
                    <div class="p-2">
                        <input class="form-check-input" type="checkbox" value="" id="all-check-bottom">
                        <label class="form-check-label" for="all-check-bottom">
                            전체 선택
                            <span>(</span>
                            <span class="check-count"></span>
                            <span>/</span>
                            <span class="sale-status-count"></span>
                            <span>)</span>
                        </label>
                    </div>
                    <div class="p-2 ms-auto">
                        <span class="check-delete" style="cursor: pointer">선택 삭제</span>
                    </div>
                </div>
            </div>`);

$(document).ready(function () {
    $.get("/api/cart/list", function (data) {
        let listArea = listDiv.clone();
        $('#cart-area').append(listArea);
        listArea.find('.check-count').text(data.checkCount);
        listArea.find('.sale-status-count').text(data.saleStatusCount);
        addAllCheckEvent(listArea.find('#all-check-top'));
        addAllCheckEvent(listArea.find('#all-check-bottom'));
        addList(data.refrigerated, listArea.find('#refrigerated-list'), listArea.find('#refrigerated-div'));
        addList(data.frozen, listArea.find('#frozen-list'), listArea.find('#frozen-div'));
        addList(data.roomTemperature, listArea.find('#room-temperature-list'), listArea.find('#room-temperature-div'));
        addStopList(data.stop, listArea.find('#stop-list'), listArea.find('#stop-div'));

        var $checkbox = $('#stop-div').find('.form-check-input');
        if ($checkbox.length > 0) {
            $checkbox.remove();
        }

        addCheckDeleteEvent(listArea.find('.check-delete'));
        checkCheckbox();
        calculateTotalPrice();
    });
    addOrderBtn();
});

function checkCheckbox() {
    var allChecked = true;
    $('.accordion .form-check-input').each(function() {
        if (!$(this).prop('checked')) {
            allChecked = false;
            return false;
        }
    });

    if (allChecked) {
        $("#all-check-top").prop('checked', true);
        $("#all-check-bottom").prop('checked', true);
    } else {
        $("#all-check-top").prop('checked', false);
        $("#all-check-bottom").prop('checked', false);
    }
}

function addCheckDeleteEvent($btn) {
    $btn.on('click', function () {
        if (!confirm("삭제하시겠습니까?")) {
            return;
        }
        var cartIds = [];

        $('.accordion .form-check-input:checked').each(function() {
            var cartId = $(this).parent().parent().attr('id');
            if (cartId) {
                cartIds.push(cartId);
            }
        });

        if (cartIds.length === 0) {
            alert('선택된 상품이 없습니다.');
        } else {
            deleteCarts(cartIds);
        }
    });
}

function addAllCheckEvent($check) {
    $check.on('click', function() {
        if ($check.prop('checked')) {
            changeAllCheckStatus(true);
            $('#all-check-top').prop('checked', true);
            $('#all-check-bottom').prop('checked', true);
            $('.form-check-input').prop('checked', true);
            $('.check-count').text($('#sale-status-count').text());
        } else {
            changeAllCheckStatus(false);
            $('#all-check-top').prop('checked', false);
            $('#all-check-bottom').prop('checked', false);
            $('.form-check-input').prop('checked', false);
            $('.check-count').text(0);
        }
        calculateTotalPrice();
    });
}

function changeCheckStatus(id, checkStatus) {
    $.ajax({
        url: "/api/cart/change/check-status",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            id: id,
            checkStatus: checkStatus
        })
    });
}

function changeAllCheckStatus(checkStatus) {
    $.ajax({
        url: "/api/cart/change/all-check-status",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify(checkStatus)
    });
}

function addList (data, $list, $div) {
    if (data.length === 0) {
        $div.remove();
    } else {
        data.forEach(function (item) {
            let area = goodDiv.clone();
            area.attr('id', item.id);
            area.find('.form-check-input').prop('checked', item.checkStatus);
            area.find('.option-name').text(item.optionName);
            area.find('.good-name').text(item.goodName);
            area.find('.option-quantity').val(item.quantity);
            addCheckEvent(area.find('.form-check-input'));
            addMinusBtn(area.find('.minus-btn'), area.find('.option-quantity'));
            addPlusBtn(area.find('.plus-btn'), area.find('.option-quantity'));
            addCloseBtn(area.find('.btn-close'), area);
            countEvent(area.find('.option-quantity'));
            setImage(item.photo, area.find('.good-photo'))
            area.find('.good-photo-href').attr('href', '/goods/' + item.goodId);
            if (item.discountPrice === null) {
                area.find('.good-price').text(item.price.toLocaleString() + "원");
                area.find('.good-discount-price').remove();
                area.find('.good-original-price').remove();
            } else {
                area.find('.good-price').remove();
                area.find('.good-discount-price').text(item.discountPrice.toLocaleString() +"원");
                area.find('.good-original-price').text(item.price.toLocaleString() + "원");
            }
            $list.append(area);
        });
    }
}

function addStopList (data, $list, $div) {
    if (data.length === 0) {
        $div.remove();
    } else {
        data.forEach(function (item) {
            let area = goodDiv.clone();
            area.attr('id', item.id);
            area.find('.form-check-input').prop('checked', item.checkStatus);
            area.find('.option-name').text(item.optionName);
            area.find('.good-name').text(item.goodName);
            area.find('.option-quantity').val(item.quantity);
            area.find('.form-check-input').remove();
            area.find('.option-quantity').remove();
            area.find('.minus-btn').remove();
            area.find('.plus-btn').remove();
            addCloseBtn(area.find('.btn-close'), area);
            setImage(item.photo, area.find('.good-photo'))
            area.find('.good-photo-href').attr('href', '/goods/' + item.goodId);
            area.find('.good-price').remove();
            area.find('.good-discount-price').remove();
            area.find('.good-original-price').remove();
            $list.append(area);
        });
    }
}

function countEvent($input) {
    $input.on('change', function () {
        if ($input.val() < 1) {
            $input.val(1);
        }
        changeCount($input.val(), $input.parent().parent().parent().attr('id'));
    });
}

function addMinusBtn($btn, $input) {
    $btn.on('click', function() {
        var currentAmount = parseInt($input.val());
        if (currentAmount > 1) {
            $input.val(currentAmount - 1);
        }
        changeCount($input.val(), $input.parent().parent().parent().attr('id'));

    });
}

function changeCount(quantity, id) {
    $.ajax({
        url: "/api/cart/change/count",
        type: "POST",
        contentType: "application/json",
        data: JSON.stringify({
            id: id,
            quantity: quantity
        }),
        success: function (data) {
            const cartElement = $(`#${data.cartId}`);

            if (data.discountPrice === null) {
                cartElement.find('.good-price').text(data.price.toLocaleString() + "원");
            } else {
                cartElement.find('.good-discount-price').text(data.discountPrice.toLocaleString() + "원");
                cartElement.find('.good-original-price').text(data.price.toLocaleString() + "원");
            }
            calculateTotalPrice();
        },
        error: function(xhr, status, error) {
            alert(xhr.responseText);
        }
    });
}

function calculateTotalPrice() {
    let totalSum = 0;
    let originalprice = 0;
    $('.row.align-items-center').each(function() {
        const isChecked = $(this).find('.form-check-input').is(':checked');
        if (isChecked) {
            if ($(this).find('.good-price').length > 0) {
                const priceText = $(this).find('.good-price').text();
                totalSum += parseInt(priceText.replace(/[^0-9]/g, ''));
                originalprice += parseInt(priceText.replace(/[^0-9]/g, ''));
            } else if ($(this).find('.good-discount-price').length > 0) {
                const priceText = $(this).find('.good-discount-price').text();
                totalSum += parseInt(priceText.replace(/[^0-9]/g, ''));
                originalprice += parseInt($(this).find('.good-original-price').text().replace(/[^0-9]/g, ''));
            }
        }
    });

    $('#total-price').text(totalSum.toLocaleString() + " 원");
    if (totalSum >= 40000) {
        $('#delivery-price').text(0 + " 원");
        $('#total-pay-price').text(totalSum.toLocaleString() + " 원");
    } else {
        $('#total-pay-price').text((totalSum + 3000).toLocaleString() + " 원");
    }

    $('#total-discount-price').text((originalprice - totalSum).toLocaleString() + " 원");
}

function addPlusBtn($btn, $input) {
    $btn.on('click', function() {
        var currentAmount = parseInt($input.val());
        $input.val(currentAmount + 1);
        changeCount($input.val(), $input.parent().parent().parent().attr('id'));
    });
}

function addCheckEvent($check) {
    $check.on('click', function () {
        var currentCount = parseInt($('#check-count').text());
        if ($check.prop('checked')) {
            $('.check-count').empty().text(currentCount + 1);
        } else {
            $('.check-count').empty().text(currentCount - 1);
        }
        checkCheckbox();
        changeCheckStatus($check.parent().parent().attr('id'), $check.prop('checked'));
        calculateTotalPrice();
    });
}

function addCloseBtn($btn, div) {
    $btn.on('click', function() {
        const cartIds = [div.attr('id')];
        deleteCart(cartIds, div);
        calculateTotalPrice();
    });
}

function deleteCarts(cartIds) {
    $.ajax({
        url: "/api/cart",
        type: "DELETE",
        contentType: "application/json",
        data: JSON.stringify(cartIds),
        success: function() {
            location.reload();
        },
        error: function(xhr, status, error) {
            alert(xhr.responseText);
        }
    });
}

function deleteCart(cartIds, div) {
    $.ajax({
        url: "/api/cart",
        type: "DELETE",
        contentType: "application/json",
        data: JSON.stringify(cartIds),
        success: function() {
            if (div.parent().parent().parent().attr('id') !== "stop-div") {
                if (div.find('.form-check-input').prop('checked')) {
                    var currentCount = parseInt($('#check-count').text());
                    $('.check-count').empty().text(currentCount - 1);
                }
                var currentSaleCount = parseInt($('#sale-status-count').text());
                $('.sale-status-count').empty().text(currentSaleCount - 1);
            }

            if (div.parent().children().length === 1) {
                div.parent().parent().parent().remove();
            } else {
                div.remove();
            }
            calculateTotalPrice();
        },
        error: function(xhr, status, error) {
            alert(xhr.responseText);
        }
    });
}

function setImage(photo, $element) {
    $.get("/images/" + photo, function (data) {
        $element.attr('src', "data:image/jpeg;base64," + data);
    });
}

function addOrderBtn() {
    $('#order').click(function () {
        location.href = '/order';
    });
}