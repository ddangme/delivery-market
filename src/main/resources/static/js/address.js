function findAddress() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = '';
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }
            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("road").value = addr;
            document.getElementById("detail").focus();
        }
    }).open();
}

function addAddress() {
    if (document.getElementById('zipcode').value === "") {
        return alert("주소 정보를 입력해주세요.");
    }
    if (document.getElementById('recipientName').value === "") {
        return alert("받으실 분의 이름을 입력해주세요.");
    }
    if (document.getElementById('recipientPhone').value === "") {
        return alert("휴대전화를 입력해주세요.");
    }

    document.getElementById('add-address-from').submit();
}

function openEditAddressModal(data) {
    let element = data.parentElement.parentElement.children[1];
    document.getElementById('edit-id').value = element.children[0].textContent;
    document.getElementById('edit-road').value = element.children[1].textContent;
    document.getElementById('edit-detail').value = element.children[2].textContent;
    document.getElementById('edit-zipcode').value = element.children[3].textContent;
    document.getElementById('edit-recipientName').value = element.children[4].textContent;
    document.getElementById('edit-recipientPhone').value = element.children[5].textContent;

    if (element.children[7].textContent === "true") {
        document.getElementById('edit-main').checked = true;
        document.getElementById('edit-main').hidden = true;
        document.getElementById('edit-main-label').hidden = true;
    } else {
        document.getElementById('edit-main').checked = false;
        document.getElementById('edit-main').hidden = false;
        document.getElementById('edit-main-label').hidden = false;
    }

}
function findAddressInEdit() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = '';
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }
            document.getElementById('edit-zipcode').value = data.zonecode;
            document.getElementById("edit-road").value = addr;
            document.getElementById('edit-detail').value = '';
            document.getElementById("edit-detail").focus();
        }
    }).open();
}

function editAddress() {
    if (document.getElementById('edit-zipcode').value === "") {
        return alert("주소 정보를 입력해주세요.");
    }
    if (document.getElementById('edit-recipientName').value === "") {
        return alert("받으실 분의 이름을 입력해주세요.");
    }
    if (document.getElementById('edit-recipientPhone').value === "") {
        return alert("휴대전화를 입력해주세요.");
    }

    document.getElementById('edit-address-from').submit();
}
function delAddress() {
    if (!confirm("배송지를 삭제하시겠습니까?")) {
        return;
    }
    let url = '/api/my-page/address/' + document.getElementById('edit-id').value;
    var xhr = new XMLHttpRequest();
    xhr.open('DELETE', url, true);

    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                location.reload();
            } else if (xhr.status === 401) {
                alert("삭제 권한이 없는 주소입니다.")
            } else if (xhr.status === 403) {
                alert("기본 배송지는 삭제할 수 없습니다. 기본 배송지를 변경한 후 시도해주세요.");
            } else {
                alert("오류가 발생했습니다. 다시 시도해주세요.");
            }
        }
    };

    xhr.send();
}