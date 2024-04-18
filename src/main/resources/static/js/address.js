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

function delAddress() {

}