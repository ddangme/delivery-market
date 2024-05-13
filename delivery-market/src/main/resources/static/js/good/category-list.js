$(document).ready(function(){


    var currentUrl = window.location.href;
    $.ajax({
        url: '/api' + currentUrl.substring(currentUrl.lastIndexOf('/categories')),
        method: 'GET',
        success: function(response) {
            // 각 데이터 항목에 대한 처리
            $(".place-holder-area").remove();
            response.content.forEach(function(dataItem) {
                let photoSrc = null;
                var imageUrl = "/api/goods/images/entity/" + dataItem.photo;

                // 이미지를 가져오는 함수 호출
                fetchImage(imageUrl, function(data) {
                    var imageType = "image/jpeg"; // 이미지 타입에 따라 적절히 변경해야 합니다.
                    var base64Image = "data:" + imageType + ";base64," + data; // Base64로 인코딩된 이미지 데이터
                    photoSrc = base64Image;

                    // 카드 생성 및 기타 작업 수행
                    createCard(dataItem, photoSrc);
                }, function(xhr, status, error) {
                    // 이미지 가져오기 실패한 경우 처리
                });
            });
            var pageInfo = {
                totalPages: response.totalPages,
                totalElements: response.totalElements,
                pageNumber: response.pageable.pageNumber,
                pageSize: response.pageable.pageSize,
                sort: response.pageable.sort
            }
            createPage(pageInfo);
        },
        error: function (xhr) {
            alert(xhr.responseText);
            window.history.back();
        },
    })

    function fetchImage(imageUrl, successCallback, errorCallback) {
        $.get(imageUrl, function(data) {
            successCallback(data);
        }).fail(function(xhr, status, error) {
            console.error("이미지를 받아오는 중 에러 발생:", status, error);
            errorCallback(xhr, status, error);
        });
    }

    function createCard(dataItem, photoSrc) {
        // 카드 요소 생성
        var $card = $('<div class="col border-light" height="470.930px" style="cursor: pointer;">\
                <div class="card">\
                    <img class="card-img-top" height="334.688px" width="260px">\
                    <div class="card-body">\
                        <div class="h5 card-title mb-0"><span class="good-name"></span></div>\
                        <p class="card-text"><span class="small text-body-secondary good-summary"></span></p>\
                    </div>\
                </div>\
              </div>');

        if (dataItem.discountPercent === null) {
            $card.find('.card-body').append('<p class="card-text good-price-area"><span class="fw-bolder good-price"></span></p>');
        } else {
            $card.find('.card-body').append('<p class="card-text good-discount-price-area">\
                                        <span class="price mb-0 text-decoration-line-through good-price" style="color: gray"></span><br>\
                                        <span class="fw-bolder good-discount-percent" style="color: rgb(250, 98, 47);"></span>\
                                        <span class="fw-bolder good-discount-price"></span>\
                                      </p>');
        }

        $('#good-list').append($card);



        // 카드 내부의 요소에 데이터 값을 대입
        var $card = $('#good-list').children().last(); // 마지막으로 추가된 카드 요소 선택



        $card.find('.good-name').text(dataItem.name);
        $card.find('.good-summary').text(dataItem.summary);
        if (dataItem.discountPercent === null) {
            $card.find('.good-price').text(dataItem.price.toLocaleString() + "원");
        } else {
            $card.find('.good-price').text(dataItem.price.toLocaleString() + "원");
            $card.find('.good-discount-percent').text(dataItem.discountPercent + "% ");
            $card.find('.good-discount-price').text(dataItem.discountPrice.toLocaleString() + "원");
        }
        // 카드 클릭 이벤트 처리
        $card.on('click', function() {
            window.location.href = '/goods/' + dataItem.id;
        });

        $card.find('.card-img-top').attr('src', photoSrc);
    }

    function createPage(pageInfo) {
// 이전 페이지 링크 설정
        var previousPageLink = $('#pagination .page-item:first-child .page-link');
        if (pageInfo.pageNumber <= 0) {
            previousPageLink.addClass('disabled').attr('href', '#');
        } else {
            previousPageLink.removeClass('disabled').attr('href', '/categories?page=' + (pageInfo.pageNumber - 1));
        }

// 페이지 번호 링크 설정
        $('#pagination .page-item:not(:first-child):not(:last-child)').remove(); // 이전 페이지 번호 제거
        for (var i = 0; i < pageInfo.totalPages; i++) {
            var pageNumber = i + 1;
            var pageItem = $('<li class="page-item"></li>');
            var pageLink = $('<a class="page-link" href="/categories?page=' + i + '">' + pageNumber + '</a>');
            if (i === pageInfo.pageNumber) {
                pageLink.addClass('disabled');
            }
            pageItem.append(pageLink);
            $('#pagination .page-item:last-child').before(pageItem);
        }

// 다음 페이지 링크 설정
        var nextPageLink = $('#pagination .page-item:last-child .page-link');
        if (pageInfo.pageNumber >= pageInfo.totalPages - 1) {
            nextPageLink.addClass('disabled').attr('href', '#');
        } else {
            nextPageLink.removeClass('disabled').attr('href', '/categories?page=' + (pageInfo.pageNumber + 1));
        }
    }
});