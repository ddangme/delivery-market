const pickTrueSvc = `
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart-fill" viewBox="0 0 16 16" style="color: red">
    <path fill-rule="evenodd" d="M8 1.314C12.438-3.248 23.534 4.735 8 15-7.534 4.736 3.562-3.248 8 1.314"/>
</svg>`;

const pickFalseSvc = `
<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-heart" viewBox="0 0 16 16" style="color: red">
    <path d="m8 2.748-.717-.737C5.6.281 2.514.878 1.4 3.053c-.523 1.023-.641 2.5.314 4.385.92 1.815 2.834 3.989 6.286 6.357 3.452-2.368 5.365-4.542 6.286-6.357.955-1.886.838-3.362.314-4.385C13.486.878 10.4.28 8.717 2.01zM8 15C-7.333 4.868 3.279-3.04 7.824 1.143q.09.083.176.171a3 3 0 0 1 .176-.17C12.72-3.042 23.333 4.867 8 15"/>
</svg>`;

const goodId = window.location.href.split('/').pop();

$(document).ready(function () {
    getGood();
    optionClickEvent();
    closeBtnEvent();
    addPickBtnEvent();
    pick();
    cart();
});

function extractOptions() {
    let options = [];

    $('#choice-options .list-group-item').each(function() {
        const optionId = $(this).attr('id');
        const quantity = $(this).find('.option-quantity').val();

        const option = {
            optionId: optionId,
            quantity: quantity
        };

        if (quantity !== "0") {
            options.push(option);
        }
    });

    return options;
}

function changeBtnPick(pickStatus) {
    if (pickStatus) {
        $('#btn-pick').html(pickTrueSvc);
    } else {
        $('#btn-pick').html(pickFalseSvc);
    }
}

function addOptionList(options) {
    var selectElement = document.getElementById("option");

    // 각 옵션을 반복하여 select 요소에 추가
    options.forEach(function(option) {
        var optionElement = document.createElement("option");
        optionElement.value = option.id; // 옵션의 id 값을 value로 설정
        if (option.discountPrice === null) {
            optionElement.textContent = option.name + " (" + option.price.toLocaleString() + "원)"
        } else {
            optionElement.textContent = option.name + " (" + option.discountPrice.toLocaleString() + "원)"
        }
        if (option.saleStatus === "품절") {
            optionElement.textContent += " (품절)";
        } else if (option.saleStatus === "재고 준비 중") {
            optionElement.textContent += " (재고 준비 중)";
        }
        selectElement.appendChild(optionElement); // select 요소에 옵션 추가
    });

    options.forEach(function(option) {
        if (option.saleStatus === "품절") {
            return;
        } else if (option.saleStatus === "재고 준비 중") {
            return;
        }
        var $listItem = $('<li class="list-group-item" hidden id=' + option.id + '></li>');
        var $row = $('<div class="row"></div>');
        var $col10 = $('<div class="col-10"></div>').append('<p>' + option.name + '</p>');
        var $col2 = $('<div class="col-2"></div>').append('<a type="button" class="w-100 btn-close" aria-label="Close"></a>');
        var $col5 = $('<div class="col-5"></div>');
        var $toolbar = $('<div class="btn-toolbar mb-3" role="toolbar" aria-label="Toolbar with button groups"></div>');
        var $btnGroup = $('<div class="btn-group me-2 ms-0" role="group" aria-label="First group"></div>');
        var $minusButton = $('<button type="button" class="btn btn-outline-secondary">-</button>');
        var $amountInput = $('<input type="text" class="form-control option-quantity" name="optionQuantity" placeholder="" value="0">');
        var $plusButton = $('<button type="button" class="btn btn-outline-secondary">+</button>');
        var $col3Offset4 = $('<div class="col-7 text-end"></div>').append('<p><span class="option-discount-price">' + option.discountPrice.toLocaleString() + '원</span><br><span class="text-decoration-line-through option-price" style="color: gray">' + option.price.toLocaleString() + '원</span></p>');
        if (option.discountPrice === null) {
            $col3Offset4 = $('<div class="col-7 text-end"></div>').append('<p><span class="option-price">' + option.price.toLocaleString() + '원</span></p>');
        }

        // - 버튼 클릭 시 동작 설정
        $minusButton.on('click', function() {
            var currentAmount = parseInt($amountInput.val());
            if (currentAmount > 1) {
                $amountInput.val(currentAmount - 1);
                updateTotalPrice();
            }
        });

        // + 버튼 클릭 시 동작 설정
        $plusButton.on('click', function() {
            var currentAmount = parseInt($amountInput.val());
            $amountInput.val(currentAmount + 1);
            updateTotalPrice();
        });

        // input 요소의 change 이벤트를 감지하여 총 가격을 업데이트하는 함수
        $amountInput.on('change', function() {
            updateTotalPrice();
        });

        // li에 요소들 추가
        $btnGroup.append($minusButton, $amountInput, $plusButton);
        $toolbar.append($btnGroup);
        $col5.append($toolbar);
        $row.append($col10, $col2, $col5, $col3Offset4);
        $listItem.append($row);

        $('.list-group').append($listItem);
    })
}


function updateTotalPrice() {
    var totalPrice = 0;
    $('.list-group-item:not([hidden])').each(function() {
        var price = parseInt($(this).find('.option-price').text().replace('원', '').replace(',', ''));
        if ($(this).find('.option-discount-price').length) {
            price = parseInt($(this).find('.option-discount-price').text().replace('원', '').replace(',', ''));
        }
        var quantity = parseInt($(this).find('.option-quantity').val());
        totalPrice += price * quantity;
    });
    $('#total-price').text(totalPrice.toLocaleString());
}

function addPickBtnEvent() {
    $('#btn-pick').click(function () {
        var url = "/api/goods/pick/" + goodId;
        $.ajax({
            url: url,
            method: 'POST',
            success: function (pickStatus) {
                changeBtnPick(pickStatus);
            },
            error: function () {
                changeBtnPick(false);
            }
        })
    });
}

function getGood() {
    $.ajax({
        url: '/api/goods/' + goodId,
        method: 'GET',
        success: function (data) {
            $('#good-name').text(data.name);
            $('#good-summary').text(data.summary);

            if (data.discountPrice === null) {
                $('#discount-area').remove();
                $('#good-price').text(data.price.toLocaleString() + "원");
            } else {
                $('#price-area').remove();
                $('#discount-percent').text(data.discountPercent + "%");
                $('#discount-price').text(data.discountPrice.toLocaleString() + "원");
            }
            getImage(data.photo);

            if (data.goodDetail.origin === "") {
                $('#good-detail-origin-area').remove();
            } else {
                $('#good-detail-origin').text(data.goodDetail.origin);
            }

            $('#good-detail-packaging-type').text(data.goodDetail.packagingType);
            $('#good-detail-weight-volume').text(data.goodDetail.weightVolume)

            if (data.goodDetail.allergyInfo === "") {
                $('#good-detail-allergyInfo-area').remove();
            } else {
                $('#good-detail-allergyInfo').text(data.goodDetail.allergyInfo);
            }
            if (data.goodDetail.guidelines === "") {
                $('#good-detail-guidelines-area').remove();
            } else {
                $('#good-detail-guidelines').text(data.goodDetail.guidelines);
            }
            if (data.goodDetail.expiryDate === "") {
                $('#good-detail-expiry-date-area').remove();
            } else {
                $('#good-detail-expiry-date').text(data.goodDetail.expiryDate);
            }
            $('#good-detail-description').html(data.goodDetail.description);

            addOptionList(data.goodOptions);

        },
        error: function(xhr) {
            alert(xhr.responseText);
            window.history.back();
        }
    });
}

function getImage(photo) {
    var imageUrl = "/images/" + photo;

    $.get(imageUrl, function(data) {
        var imgElement = document.getElementById("good-main-photo");
        imgElement.src = "data:" + "image/jpeg" + ";base64," + data;
    }).fail(function(xhr, status, error) {
        console.error("이미지를 받아오는 중 에러 발생:", status, error);
    });
}

function closeBtnEvent() {
    $(document).on('click', '.btn-close', function() {
        $(this).closest('li').attr('hidden', true);
        $(this).closest('li').find('.option-quantity').val('0');
        updateTotalPrice();
    });
}

function pick() {
    $.ajax({
        url: "/api/goods/find/pick/" + goodId,
        method: 'GET',
        success: function (pickStatus) {
            changeBtnPick(pickStatus);
        },
        error: function () {
            changeBtnPick(false);
        },
    });
}

function cart() {
    $('#btn-cart').on('click', function () {
        const options = extractOptions();
        $.ajax({
            url: "/api/cart",
            method: 'POST',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(options),
            success: function (response) {
                $('.cart-badge').text(response.quantity);
                alert(response.message);
            },
            error: function (xhr) {
                alert(xhr.responseText);
                console.log(xhr.responseText);
            }
        })
    });
}

function optionClickEvent() {
    $('#option').on('change', function() {
        var selectedOptionId = $(this).val();
        $('.list-group-item').each(function() {
            if ($(this).attr('id') === selectedOptionId) {
                if (!$(this).is(':hidden')) {
                    alert('이미 추가된 옵션입니다.');
                } else {
                    $(this).closest('li').find('.option-quantity').val('1');
                    $(this).removeAttr('hidden');
                    updateTotalPrice();
                }
            }
        });

        $(this).val('');
    });
}
