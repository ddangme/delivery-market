function addParentCategory() {
    if (!confirm("카테고리를 추가하시겠습니까?")) {
        return;
    }

    let name = $('#name').val();
    let url = '/api/categories';

    $.ajax({
        type: 'POST',
        url: '/api/categories',
        contentType: 'application/json; charset=utf-8',
        data: JSON.stringify({ name: name }),
        dataType: 'json',
        success: function(status, xhr) {
            console.log(status);
            console.log(xhr);
            alert("success");
            location.reload();
        },
        error: function(xhr, status) {
            console.log(status);
            console.log(xhr);
            if (xhr.status === 200) {
                location.reload();
            } else {
                alert(xhr.responseText);
            }
        }
    });
}

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
