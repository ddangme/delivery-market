function member() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = '';
            if (data.userSelectedType === 'R') {
                addr = data.roadAddress;
            } else {
                addr = data.jibunAddress;
            }

            document.getElementById('zipCode').value = data.zonecode;
            document.getElementById("address").value = addr;
            document.getElementById("detail").focus();

        }
    }).open();
}

function idDuplicateCheck() {
    var loginId = document.getElementById('loginId').value;

    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/id-duplicate-check', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(loginId);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("사용 가능합니다.")
            } else if (xhr.status === 409) {
                alert("이미 존재하는 아이디 입니다.");
            } else {
                alert("서버 오류가 발생했습니다. 다시 시도해주세요");
            }
        }
    };
    xhr.send(JSON.stringify(data));
}