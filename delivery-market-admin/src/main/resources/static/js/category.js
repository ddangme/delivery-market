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
    console.log(data);
    $.ajax({
        type: 'DELETE',
        url: '/api/categories',
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function(response) {
            location.reload();
        },
        error: function(xhr, status, error) {
            alert(xhr.responseText);
        }
    });

}
