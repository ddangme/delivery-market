$(document).ready(function() {
    sendCategoriesRequest();
});

function sendCategoriesRequest() {
    $.ajax({
        url: "/api/categories",
        method: "GET",
        success: function(data) {
            $.each(data.result, function(index, item) {
                var listItem = $('<li></li>');
                var link = $('<button></button>')
                    .addClass('dropdown-item fw-bold')
                    .attr('disabled', 'disabled')
                    .attr('href', '/category/' + item.id)
                    .text(item.name);
                listItem.append(link);
                $('#categories').append(listItem);

                // 만약 자식 카테고리가 있다면, 각 자식 카테고리에 대한 하위 리스트 생성
                if (item.childs && item.childs.length > 0) {
                    $.each(item.childs, function(childIndex, childItem) {
                        var childLink = $('<a></a>')
                            .addClass('dropdown-item')
                            .attr('href', '/categories/' + childItem.id)
                            .text( "    " + childItem.name);
                        listItem.append(childLink);
                    });
                }

                if (index !== data.result.length - 1) {
                    var hrLinkItem = $('<li></li>');
                    var hrLink = $('<hr>')
                        .addClass("dropdown-divider");
                    hrLinkItem.append(hrLink);
                    $('#categories').append(hrLinkItem);
                }
            });
        },
        error: function() {
            setTimeout(sendCategoriesRequest, 3000);
        }
    });
}