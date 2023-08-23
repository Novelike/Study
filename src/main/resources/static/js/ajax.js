function check() {
    let id = document.getElementById('id');
    let pw = document.getElementById('pw');
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