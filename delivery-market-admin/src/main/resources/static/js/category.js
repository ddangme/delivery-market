$(document).ready(function() {
    $(document).on('click', '#add-child-category', function() {
        var newRow = $('<div class="row"></div>');
        var col10 = $('<div class="col-10 pb-3"></div>');
        var input = $('<input type="text" class="form-control" name="childName" placeholder="이름을 입력해주세요.">');
        var col2 = $('<div class="col-2 pb-3"></div>');
        var deleteButton = $('<button type="button" class="btn btn-outline-danger w-100">삭제</button>');

        col10.append(input);
        col2.append(deleteButton);
        newRow.append(col10);
        newRow.append(col2);

        $('#add-child-category-list').append(newRow);
    });

    $(document).on('click', '#add-child-category-in-edit', function() {
        var newRow = $('<div class="row"></div>');
        var col10 = $('<div class="col-10 pb-3"></div>');
        var input = $('<input type="text" class="form-control" name="newChildName" placeholder="이름을 입력해주세요.">');
        var col2 = $('<div class="col-2 pb-3"></div>');
        var deleteButton = $('<button type="button" class="btn btn-outline-danger w-100">삭제</button>');

        col10.append(input);
        col2.append(deleteButton);
        newRow.append(col10);
        newRow.append(col2);

        $('#edit-child-category-list').append(newRow);
    });

    $(document).on('click', '.btn-outline-danger', function() {
        $(this).closest('.row').remove();
    });

    $('#add-category-btn').click(function () {
        if (!confirm("카테고리를 추가하시겠습니까?")) {
            return;
        }

        var formData = new FormData($('#add-category-form')[0]);

        $.ajax({
            url: '/api/categories',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                window.location.href = '/categories';
                alert("카테고리가 추가되었습니다.");
            },
            error: function(xhr, status) {
                alert(xhr.responseText);
            }
        });
    })


    $('[id^="del-category-"]').click(function() {
        var id = $(this).attr('id').split('del-category-')[1];

        var delCategory = $('<input hidden name="delCategoryIds" class="form-control"/>');
        delCategory.val(id);

        $('#edit-child-category-list').append(delCategory);
    });


    $('#delete-all-category').click(function() {
        if (!confirm("상위 카테고리와 하위 카테고리를 모두 삭제하시겠습니까?")) {
            return;
        }

        var id = $('#categoryId').val();
        $.ajax({
            type: 'DELETE',
            url: '/api/categories/' + id,
            contentType: 'application/json',
            success: function(response) {
                alert("카테고리가 삭제되었습니다.");
                window.location.href = '/categories';
            },
            error: function(xhr, status) {
                alert(xhr.responseText);
            }
        });

    })


    $('#edit-category-btn').click(function () {
        if (!confirm("저장하시겠습니까?")) {
            return;
        }

        var formData = new FormData($('#edit-category-form')[0]);

        $.ajax({
            type: 'POST', // HTTP 요청 방식
            url: '/api/categories/edit/' + $('#categoryId').val(), // 서버 URL
            data: formData,
            processData: false,
            contentType: false,
            success: function(response) {
                alert("저장되었습니다.");
            },
            error: function(xhr, status) {
                alert(xhr.responseText);
            }
        });
    })



});