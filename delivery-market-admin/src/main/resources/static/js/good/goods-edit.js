$(document).ready(function () {

    $(".option-field:first .delOption").hide();

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
                if (data.length > 0) {
                    $.each(data, function(index, item) {
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

    $(document).on('click', '.delOption', function() {
        $(this).closest('.option-field').remove();
    });

    $('#addOption').on('click', function() {
        var optionField = $('.option-field').first().clone();
        optionField.find('input, select').val('');
        $('#option-fields').append(optionField);
        optionField.find('.delOption').show();
    });

    $('#change-photo').on('click', function() {
        $('#photo').prop('disabled', false);
        $(this).prop('disabled', true);
        $('#before-photo').remove();
    });

    $('#goods-form').submit(function(event) {
        event.preventDefault();

        var goodId = $('.good-id').val();
        var formData = new FormData();

        var photoFile = $('#photo')[0].files[0];
        formData.append('photo', photoFile);

        formData.append('id', goodId);
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
        formData.append('goodsDetail.id', $('#detail-id').val());
        formData.append('goodsDetail.origin', $('#origin').val());
        formData.append('goodsDetail.allergyInfo', $('#allergyInfo').val());
        formData.append('goodsDetail.guidelines', $('#guidelines').val());
        formData.append('goodsDetail.expiryDate', $('#expiryDate').val());
        formData.append('goodsDetail.packagingType', $('#packagingType').val());
        formData.append('goodsDetail.weightVolume', $('#weightVolume').val());
        formData.append('goodsDetail.description', $('#summernote').val());

        $('.option-field').each(function(index, element) {// optionField 내의 데이터를 수집하여 formData에 추가합니다.
            var optionField = $(this);

            var optionId = optionField.find('.option-id').val();
            var optionName = optionField.find('.optionName').val();
            var optionSaleStatus = optionField.find('.optionSaleStatus').val();
            var optionPrice = optionField.find('.optionPrice').val();
            var optionQuantity = optionField.find('.optionQuantity').val();
            var optionDiscountPrice = optionField.find('.optionDiscountPrice').val();
            var optionDiscountPercent = optionField.find('.optionDiscountPercent').val();

            // FormData에 데이터 추가
            formData.append('goodsOptions[' + index + '].id', optionId);
            formData.append('goodsOptions[' + index + '].name', optionName);
            formData.append('goodsOptions[' + index + '].saleStatus', optionSaleStatus);
            formData.append('goodsOptions[' + index + '].price', optionPrice);
            formData.append('goodsOptions[' + index + '].quantity', optionQuantity);
            formData.append('goodsOptions[' + index + '].discountPrice', optionDiscountPrice);
            formData.append('goodsOptions[' + index + '].discountPercent', optionDiscountPercent);
        });

        $.ajax({
            type: 'POST',
            url: '/api/goods/edit',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                alert("상품이 수정되었습니다.");
            },
            error: function(xhr, status) {
                alert(xhr.responseText);
            }
        });
    });


});
$('#summernote').summernote({
    placeholder: '상품 상세 페이지에 노출될 상세 정보 입력란입니다. 최대한 자세하게 입력해주세요.',
    height: 400,
    lang: 'ko-KR',
});