$(document).ready(function() {
    $('#all').change(function() {
        var checkboxes = $('[id^="category-"]');
        $('.checkbox').prop('checked', $(this).prop('checked'));
        if ($(this).prop('checked')) {
            checkboxes.prop('checked', true);
        } else {
            checkboxes.prop('checked', false);
        }
    });

    $('[id^="category-"]').change(function() {
        if (!$(this).prop('checked')) {
            $('#all').prop('checked', false);
        }
        var checkboxes = $('[id^="category-"]');

        if (allChecked()) {
            $('#all').prop('checked', true);
        }

        function allChecked() {
            var allChecked = true;
            checkboxes.each(function() {
                if (!$(this).prop('checked')) {
                    allChecked = false;
                    return false; // each()를 종료하기 위해 false를 반환
                }
            });
            return allChecked;
        }
    });

    $(".clickable-row").click(function() {
        window.location = "/categories/" + $(this).attr("id");
    });
});


function delCategory() {

    var data = [];
    $('[id^="category-"]:checkbox:checked').each(function() {
        var id = $(this).attr('id');
        var number = id.replace('category-', '');
        data.push(parseInt(number));
    });

    if (data.length === 0) {
        return alert("선택된 카테고리가 없습니다.");
    }

    if (data.length === 1) {
        if (!confirm("카테고리를 삭제하시겠습니까?")) {
            return;
        }
    } else {
        if (!confirm("선택하신 " + data.length + "개의 카테고리를 삭제하시겠습니까?")) {
            return;
        }
    }

    console.log(data);
    $.ajax({
        type: 'DELETE',
        url: '/api/categories',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(response) {
            if (data.length === 1) {
                alert("카테고리가 삭제되었습니다.");
            } else {
                alert("선택하신 " + data.length + "개의 카테고리가 삭제되었습니다.");
            }
            location.reload();
        },
        error: function(xhr, status, error) {
            if (data.length === 1) {
                alert(xhr.responseText);
            } else {
                alert("삭제할 수 없는 카테고리가 존재합니다.");
            }
        }
    });

}
