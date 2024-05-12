$(document).ready(function() {
    function addCommas(number) {
        if (number !== null) {
            return number.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
        }
    }


    $.ajax({
        url: '/api/goods/' + window.location.href.split('/').pop(),
        method: 'GET',
        success: function(data) {
            data.price = addCommas(data.price);
            data.discountPrice = addCommas(data.discountPrice);

            $('#good-name').html('<h5>' + data.name + '</h5>');
            $('#good-summary').html('<p style="color: gray">' + data.summary + '</p>');
            if (typeof data.discountPrice === 'undefined') {
                $('#good-price').html('<h4 class="mb"><span>' + data.price + '원</span></h4>')
                $('#good-discount-price').remove();
            } else {
                $('#good-discount-price').html('<h4 class="mb-0"><span style="color: rgb(250, 98, 47);">' + data.discountPercent + '%  </span><span>' + data.discountPrice + '원</span></h4><p class="text-decoration-line-through" style="color: gray">' + data.price + '원</p>');
                $('#good-price').remove();
            }
            var imageUrl = "/api/goods/images/entity/" + data.photo;

            $.get(imageUrl, function(data) {
                // 이미지를 받아오는 데 성공했을 때 실행되는 함수
                var imgElement = document.getElementById("good-main-photo");
                var imageType = "image/jpeg"; // 이미지 타입에 따라 적절히 변경해야 합니다.
                var base64Image = "data:" + imageType + ";base64," + data; // Base64로 인코딩된 이미지 데이터
                imgElement.src = base64Image; // 이미지 태그의 src 속성에 설정하여 화면에 표시
            }).fail(function(xhr, status, error) {
                // 이미지를 받아오는 데 실패했을 때 실행되는 함수
                console.error("이미지를 받아오는 중 에러 발생:", status, error);
            });

            if (data.goodDetail.origin === "") {
                $('#good-detail-origin').remove();
            } else {
                $('#good-detail-origin').html('<h5>원산지: ' + data.goodDetail.origin + '</h5>');
            }
            $('#good-detail-packaging-type').html('<div class="col-3"><p class="my-2">포장타입</p></div><div class="col-9"><p class="my-2">' + data.goodDetail.packagingType + '</p></div>');
            $('#good-detail-weight-volume').html('<div class="col-3"><p class="my-2">중량/용량</p></div><div class="col-9"><p class="my-2">' + data.goodDetail.weightVolume + '</p></div>');
            if (data.goodDetail.allergyInfo === "") {
                $('#good-detail-allergyInfo').remove();
            } else {
                $('#good-detail-allergyInfo').html('<div class="col-3"><p class="my-2">알레르기 정보</p></div><div class="col-9"><p class="my-2">' + data.goodDetail.allergyInfo + '</p></div>');
            }
            if (data.goodDetail.guidelines === "") {
                $('#good-detail-guidelines').remove();
            } else {
                $('#good-detail-guidelines').html('<div class="col-3"><p class="my-2">안내사항</p></div><div class="col-9"><p class="my-2">' + data.goodDetail.guidelines + '</p></div>');
            }
            if (data.goodDetail.expiryDate === "") {
                $('#good-detail-expiry-date').remove();
            } else {
                $('#good-detail-expiry-date').html('<div class="col-3"><p class="my-2">소비기한(또는 유통기한)</p></div><div class="col-9"><p class="my-2">' + data.goodDetail.expiryDate + '</p></div>');
            }
            options = data.goodOptions;
            var selectElement = document.getElementById("option");

            // 각 옵션을 반복하여 select 요소에 추가
            options.forEach(function(option) {
                var optionElement = document.createElement("option");
                optionElement.value = option.id; // 옵션의 id 값을 value로 설정
                if (option.discountPrice === null) {
                    optionElement.textContent = option.name + " (" + addCommas(option.price) + "원)"
                } else {
                    optionElement.textContent = option.name + " (" + addCommas(option.discountPrice) + "원)"
                }
                selectElement.appendChild(optionElement); // select 요소에 옵션 추가
            });

            options.forEach(function(option) {
                var $listItem = $('<li class="list-group-item" hidden id=' + option.id + '></li>');
                var $row = $('<div class="row"></div>');
                var $col10 = $('<div class="col-10"></div>').append('<p>' + option.name + '</p>');
                var $col2 = $('<div class="col-2"></div>').append('<a type="button" class="w-100 btn-close" aria-label="Close"></a>');
                var $col5 = $('<div class="col-5"></div>');
                var $toolbar = $('<div class="btn-toolbar mb-3" role="toolbar" aria-label="Toolbar with button groups"></div>');
                var $btnGroup = $('<div class="btn-group me-2 ms-0" role="group" aria-label="First group"></div>');
                var $minusButton = $('<button type="button" class="btn btn-outline-secondary">-</button>');
                var $amountInput = $('<input type="text" class="form-control option-amount" name="optionAmount" placeholder="" value="0">');
                var $plusButton = $('<button type="button" class="btn btn-outline-secondary">+</button>');
                var $col3Offset4 = $('<div class="col-7 text-end"></div>').append('<p><span class="option-discount-price">' + addCommas(option.discountPrice) + '원</span><br><span class="text-decoration-line-through option-price" style="color: gray">' + addCommas(option.price) + '원</span></p>');
                if (option.discountPrice === null) {
                    $col3Offset4 = $('<div class="col-7 text-end"></div>').append('<p><span class="option-price">' + addCommas(option.price) + '원</span></p>');
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
                $('#choice-options ul').append($listItem);

            })

            var descriptionHtml = data.goodDetail.description;
            $('#good-detail-description').html(descriptionHtml);
        },
        error: function(xhr, status) {
            alert(xhr.responseText);
            window.history.back();
        }
    });



    function updateTotalPrice() {
        var totalPrice = 0;
        $('.list-group-item:not([hidden])').each(function() {
            var price = parseInt($(this).find('.option-price').text().replace('원', '').replace(',', ''));
            if ($(this).find('.option-discount-price').length) {
                price = parseInt($(this).find('.option-discount-price').text().replace('원', '').replace(',', ''));
            }
            console.log(price);
            var amount = parseInt($(this).find('.option-amount').val());
            console.log(amount);
            totalPrice += price * amount;
        });
        $('#total-price').text(addCommas(totalPrice));
    }

    $('#option').on('change', function() {
        var selectedOptionId = $(this).val();
        $('.list-group-item').each(function() {
            if ($(this).attr('id') === selectedOptionId) {
                if (!$(this).is(':hidden')) {
                    alert('이미 추가된 옵션입니다.');
                } else {
                    $(this).closest('li').find('.option-amount').val('1');
                    $(this).removeAttr('hidden');
                    updateTotalPrice();
                }
            }
        });

        $(this).val('');
    });

    $(document).on('click', '.btn-close', function() {
        // 현재 close 버튼이 속한 li 요소를 찾아 hidden 속성을 추가하여 해당 영역을 숨김 처리
        $(this).closest('li').attr('hidden', true);

        // 해당 영역의 input 요소를 찾아서 값을 1로 설정
        $(this).closest('li').find('.option-amount').val('0');
        updateTotalPrice();
    });
});
