$(document).ready(function(){
    $('#signin').click(function() {
        $.ajax({
            type: 'post',
            url: 'sign/signin.html',
            success: function () {
                location.href = "/sign/signin.html";
            }
        })
    });
    $('.signup').on("click", function() {
        alert('성공!');
    });
});