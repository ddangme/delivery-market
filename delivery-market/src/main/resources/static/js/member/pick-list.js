const goodDiv = $(`
    <div class="col-12 mt-3 mb-2">
        <div class="row">
            <div class="col-2">
                <img class="good-photo" style="width: 90px; height: 117px; cursor: pointer">
            </div>
            <div class="col-10">
                <p hidden>
                    <span class="good-id"></span>
                </p>
                <p class="mb-0">
                    <span class="fw-semibold good-name"></span>
                </p>
                <p style="margin-bottom: 38px;" class="good-price-area">
                    <span class="good-price"></span>
                <p style="margin-bottom: 38px;" class="good-discount-area">
                    <span class="good-discount-percent" style="color: rgb(250, 98, 47);"></span>
                    <span class="good-discount-price"></span>
                    <span class="text-decoration-line-through good-original-price" style="color: gray"></span>
                </p>
                <div class="row">
                    <div class="col-6">
                        <button type="button" class="btn btn-secondary btn-sm w-100 delete-pick">삭제</button>
                    </div>
                    <div class="col-6">
                        <button type="button" class="w-100 btn btn-warning btn-sm">구매</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
`);
const goodList = $("#good-list");
$(document).ready(function () {
    $.ajax({
        url: "/api/my-page/pick/list",
        method: 'GET',
        success: function (data) {
            $(document).find('.good-count').text(data.length);
            data.forEach(function (good) {
                let append = goodDiv.clone();
                append.find('.good-id').text(good.id);
                append.find('.good-name').text(good.name);
                if (good.discountPercent === null) {
                    append.find('.good-price').text(good.price.toLocaleString() + "원");
                    append.find('.good-discount-area').remove();
                } else {
                    append.find('.good-discount-price').text(good.discountPrice.toLocaleString() + "원");
                    append.find('.good-discount-percent').text(good.discountPercent + "%");
                    append.find('.good-original-price').text(good.price.toLocaleString() + "원");
                    append.find('.good-price-area').remove();
                }
                setImage(good.photo, append.find('.good-photo'))
                append.find('.good-photo').click(function() {
                    window.location.href = '/goods/' + good.id;
                });
                append.find('.delete-pick').click(function () {
                    if (!confirm("삭제하시겠습니까?")) {
                        return;
                    }
                    var $clickedButton = $(this); // 클릭된 버튼을 $clickedButton 변수에 저장

                    var goodId = $clickedButton.closest('.col-12').find('.good-id').text();
                    $.ajax({
                        url: '/api/goods/pick/' + goodId,
                        method: 'DELETE',
                        success: function() {
                            $clickedButton.closest('.col-12').remove(); // $clickedButton 변수를 사용하여 클릭된 버튼의 부모인 .col-12를 삭제
                            var beforeCount = $(document).find('.good-count').text();
                            $(document).find('.good-count').text(beforeCount - 1);
                        },
                        error: function(xhr) {
                            alert(xhr.responseText);
                        }
                    });
                });

                goodList.append(append);
            });
        },
        error: function (xhr) {
            alert(xhr.responseText);
            window.history.back();
        }
    })
});

function setImage(photo, $element) {
    $.get("/images/" + photo, function (data) {
        $element.attr('src', "data:" + "image/jpeg" + ";base64," + data);
    });
}