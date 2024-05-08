$(document).ready(function() {
    $('#parentCategoryId').change(function() {
        var parentId = $(this).val();
        if (parentId === "") {
            $('#childCategoryId').empty();
            $('#childCategoryId').prop('disabled', true);

            return;
        }
        $.ajax({
            url: '/api/categories/' + parentId,
            method: 'GET',
            success: function(data) {
                $('#childCategoryId').empty();
                if (data.result.length > 0) {
                    $.each(data.result, function(index, item) {
                        $('#childCategoryId').append($('<option>', {
                            value: item.id,
                            text: item.name
                        }));
                    });
                    $('#childCategoryId').prop('disabled', false);
                } else {
                    $('#childCategoryId').prop('disabled', true);
                }

            },
            error: function(xhr, textStatus, errorThrown) {
                alert("서버 오류가 발생했습니다. 상위 카테고리를 다시 선택해 주세요.");
            }
        });
    });

    $('#addOption').on('click', function() {
        var optionField = $('.option-field').first().clone();
        optionField.find('input, select').val('');
        $('#option-fields').append(optionField);
        optionField.find('.delOption').removeAttr('hidden'); // 버튼을 숨기지 않음
    });


    $('#goods-form').submit(function(event) {
        event.preventDefault();

        var formData = new FormData();

        var photoFile = $('#photo')[0].files[0];
        formData.append('photo', photoFile);

        formData.append('name', $('#name').val());
        formData.append('summary', $('#summary').val());
        if ($('#childCategoryId').val() === null) {
            formData.append('categoryId', $('#parentCategoryId').val());
        } else {
            formData.append('categoryId', $('#childCategoryId').val());
        }
        formData.append('saleStatus', $('#saleStatus').val());
        formData.append('price', $('#price').val());
        formData.append('discountPrice', $('#discountPrice').val());
        formData.append('discountPercent', $('#discountPercent').val());
        formData.append('discountStatus', $('#discountStatus').val());
        formData.append('goodsDetail.origin', $('#origin').val());
        formData.append('goodsDetail.allergyInfo', $('#allergyInfo').val());
        formData.append('goodsDetail.guidelines', $('#guidelines').val());
        formData.append('goodsDetail.expiryDate', $('#expiryDate').val());
        formData.append('goodsDetail.packagingType', $('#packagingType').val());
        formData.append('goodsDetail.weightVolume', $('#weightVolume').val());
        formData.append('goodsDetail.description', $('#summernote').val());

        $('.option-field').each(function(index, element) {
            var optionField = $(element);

            var name = optionField.find('.optionName').val();
            var saleStatus = optionField.find('.optionSaleStatus').val();
            var price = optionField.find('.optionPrice').val();
            var discountPrice = optionField.find('.optionDiscountPrice').val();
            var discountPercent = optionField.find('.optionDiscountPercent').val();
            var amount = optionField.find('.optionAmount').val();

            formData.append('goodsOptions[' + index + '].name', name);
            formData.append('goodsOptions[' + index + '].price', price);
            formData.append('goodsOptions[' + index + '].discountPrice', discountPrice);
            formData.append('goodsOptions[' + index + '].discountPercent', discountPercent);
            formData.append('goodsOptions[' + index + '].amount', amount);
            formData.append('goodsOptions[' + index + '].saleStatus', saleStatus);
        });

        $.ajax({
            type: 'POST',
            url: '/api/goods/add',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                if (response.resultCode === "SUCCESS") {
                    window.location.href = '/goods';
                    alert("상품이 추가되었습니다.");
                }
            },
            error: function(xhr, status) {
                alert(xhr.responseText);
            }
        });
    });

    $(document).on('click', '.delOption', function() {
        $(this).closest('.option-field').remove();
    });

});
$('#summernote').summernote({
    placeholder: '상품 상세 페이지에 노출될 상세 정보 입력란입니다. 최대한 자세하게 입력해주세요.',
    height: 400,
    lang: 'ko-KR',
});