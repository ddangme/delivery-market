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

    var pattern = /^[a-zA-Z0-9]{5,20}$/;

    if (!pattern.test(loginId)) {
        document.getElementById('loginId-ok').style.display = 'none';
        document.getElementById('loginId-error').textContent = '5자~20자의 영문 혹은 영문과 숫자의 조합으로 입력해주세요.';
        document.getElementById('loginId-error').style.display = 'block';
        document.getElementById("loginId").className = "form-control field-error";

        return;
    }


    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/id-duplicate-check', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(loginId);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                document.getElementById('loginId-error').style.display = 'none';
                document.getElementById("loginId").className = "form-control";
                document.getElementById('loginId-ok').style.display = 'block';
            } else if (xhr.status === 409) {
                document.getElementById('loginId-ok').style.display = 'none';
                document.getElementById('loginId-error').textContent = '이미 사용 중인 아이디 입니다.';
                document.getElementById('loginId-error').style.display = 'block';
                document.getElementById("loginId").className = "form-control field-error";
            } else {
                alert("서버 오류가 발생했습니다. 다시 시도해주세요");
            }
        }
    };
}