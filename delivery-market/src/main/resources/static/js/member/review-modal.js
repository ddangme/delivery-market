const modal = $(`<div class="row" id="review-form-area">
                        <label for="content" class="col-3 col-form-label">
                            내용
                            <span class="text-danger">
                                *
                            </span>
                        </label>
                        <div class="col-9 mb-2">
                            <textarea class="form-control" style="height: 240px" placeholder="상품 특성에 맞는 후기를 작성해주세요. 예) 레시피, 겉포장 속 실제 구성품 사진, 플레이팅 등 (10자 이상 ~ 최대 1,000자 이하)"
                                      id="content" name="content"></textarea>
                        </div>
                        <label for="photos" class="col-3 col-form-label">사진 첨부<br>
                            <small class="text-secondary">
                                최대 8장
                            </small></label>
                        <div class="col-9">
                            <input class="form-control form-control-sm" name="photos" id="photos" type="file" multiple accept="image/*">
                        </div>
                        <label class="col-3 col-form-label">별점</label>
                        <div class="col-9">
                            <fieldset class="rate">
                                <input type="radio" id="rating10" name="rating" value="10"><label for="rating10" title="5점"></label>
                                <input type="radio" id="rating9" name="rating" value="9"><label class="half" for="rating9" title="4.5점"></label>
                                <input type="radio" id="rating8" name="rating" value="8"><label for="rating8" title="4점"></label>
                                <input type="radio" id="rating7" name="rating" value="7"><label class="half" for="rating7" title="3.5점"></label>
                                <input type="radio" id="rating6" name="rating" value="6"><label for="rating6" title="3점"></label>
                                <input type="radio" id="rating5" name="rating" value="5"><label class="half" for="rating5" title="2.5점"></label>
                                <input type="radio" id="rating4" name="rating" value="4"><label for="rating4" title="2점"></label>
                                <input type="radio" id="rating3" name="rating" value="3"><label class="half" for="rating3" title="1.5점"></label>
                                <input type="radio" id="rating2" name="rating" value="2"><label for="rating2" title="1점"></label>
                                <input type="radio" id="rating1" name="rating" value="1"><label class="half" for="rating1" title="0.5점"></label>
                            </fieldset>
                        </div>
                        <div class="col-12">
                              <input class="form-check-input" type="checkbox" id="secret">
                              <label class="form-check-label" for="secret">비공개</label>
                        </div>
                        <input type="hidden" id="optionId" name="optionId">
                        <input type="hidden" id="orderGoodId" name="orderGoodId">
                    </div>`);

function displayModal(optionId, goodId, $photo) {
    $('.photo').attr('src', $photo.attr('src'));
    $('#review-form-area').remove();
    const form = modal.clone();
    form.find('#optionId').val(optionId);
    form.find('#orderGoodId').val(goodId);
    $('#review-form').append(form);
}

$(document).ready(function() {
    $('#review-submit-btn').click(function() {
        const formData = new FormData();

        const photoFile = $('#photos')[0].files[0];
        formData.append('photos', photoFile);

        formData.append('content', $('#content').val());

        const rating = $('input[name="rating"]:checked').val();
        formData.append('rating', rating/2);

        formData.append('optionId', $('#optionId').val());
        formData.append('orderGoodId', $('#orderGoodId').val());

        if ($('#secret').is(':checked')) {
            formData.append('secret', true);
        } else {
            formData.append('secret', false);
        }

        $.ajax({
            url: '/api/review',
            type: 'POST',
            data: formData,
            processData: false,
            contentType: false,
            success: function() {
                location.reload();
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });
    });

    $('#photos').change(function() {
        if (this.files.length > 8) {
            alert('최대 8장 까지 등록 가능합니다.');
            this.value = '';
        }
    });
});