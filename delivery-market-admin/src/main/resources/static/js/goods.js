$(document).ready(function() {
    $('#parentCategoryId').change(function() {
        var selectedValue = $(this).val();
        console.log(selectedValue);
        $.ajax({
            url: '/api/categories/' + selectedValue,
            method: 'GET',
            success: function(data) {
                $('#categoryId').empty();
                if (data.result.length > 0) {
                    $.each(data.result, function(index, item) {
                        $('#categoryId').append($('<option>', {
                            value: item.id,
                            text: item.name
                        }));
                    });
                    $('#parentCategoryId').removeAttr('name');

                    // <select> 요소 활성화
                    $('#categoryId').prop('disabled', false);
                } else {
                    $('#categoryId').prop('disabled', true);
                    $('#parentCategoryId').attr('name', 'categoryId');
                }
            },
            error: function(xhr, textStatus, errorThrown) {
                alert("서버 오류가 발생했습니다. 상위 카테고리를 다시 선택해 주세요.");
            }
        });
    });
});