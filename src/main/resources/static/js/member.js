let checkedEmail = null;

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

function sendEmail() {
    var email = document.getElementById('email').value;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/email', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(email);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                alert("인증번호가 발급되었습니다.");
            } else if (xhr.status === 400) {
                alert("유효하지 않은 이메일입니다.")
            } else {
                alert("오류가 발생했습니다. 다시 시도해주세요.")
            }
        }
    };
}

function checkAuthCode() {
    var authCode = document.getElementById('auth-code').value;
    var xhr = new XMLHttpRequest();
    xhr.open('POST', '/api/email/auth-code', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.send(authCode);
    xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                checkedEmail = document.getElementById('email').value;
                alert("인증이 완료되었습니다.");
            } else if (xhr.status === 403) {
                alert("인증 시간이 초과되었습니다. 새로운 인증 번호 발급 후 다시 시도해주세요.");
            } else if (xhr.status === 401) {
                alert("잘못된 인증번호 입니다.");
            }
        }
    };
}

function signUp() {
    let email = document.getElementById("email").value;
    if (checkedEmail === email) {
        if (confirm("회원 가입을 진행하시겠습니까?")) {
            document.getElementById('sign-up-form').submit();
        }
    } else {
        alert("이메일 인증 시도 후 가입 가능합니다.")
    }

}