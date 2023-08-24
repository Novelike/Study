function check() {
    let id = document.getElementById('loginId');
    let pw = document.getElementById('loginPw');
    if(id.value === "") {
        alert('아이디를 입력하세요.')
        id.focus();
        return false;
    }
    if(pw.value === "") {
        alert('비밀번호를 입력하세요.');
        pw.focus();
        return false;
    }
    let pwCheck = /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,20}$/;
    if(!pwCheck.test(pw.value)) {
        alert('영문 + 숫자 + 특수문자의 조합으로 8자~20자의 비밀번호를 입력하세요.');
        pw.focus();
        return false;
    }
    document.sign_form.submit();
}

window.onload = function() {
    document.querySelector("#loginId").addEventListener("keyup", async (event) => {
        let loginId = $("#loginId").val();
        let loginPw = $("#loginPw").val();

        if (isEmptyString(loginId)) {
            setBtnLogin(false);
            return false;
        }

        if (!strCheck(loginId, "id")) {
            setBtnLogin(false);
            return false;
        }

        if (isEmptyString(loginPw)) {
            setBtnLogin(false);
            return false;
        }

        if (!strCheck(loginPw, "pwd")) {
            setBtnLogin(false);
            return false;
        }

        setBtnLogin(true);

    });

    document.querySelector("#loginPw").addEventListener("keyup", async (event) => {
        let loginPw = $("#loginPw").val();
        let loginId = $("#loginId").val();

        if (isEmptyString(loginId)) {
            setBtnLogin(false);
            return false;
        }

        if (!strCheck(loginId, "id")) {
            setBtnLogin(false);
            return false;
        }

        if (isEmptyString(loginPw)) {
            setBtnLogin(false);
            return false;
        }

        if (!strCheck(loginPw, "pwd")) {
            setBtnLogin(false);
            return false;
        }

        setBtnLogin(true);
    });

    function setBtnLogin(chkDisabled) {
        const $loginBtn = $("#signin_btn");
        $loginBtn.removeClass();
        if (chkDisabled) {
            $loginBtn.addClass("btn_primary");
        } else {
            $loginBtn.removeClass("btn_primary");
        }
    }
}