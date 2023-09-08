/* 사용자 팝업 열기 */
function fnLayerPopup(id) {
    $("#" + id).show();
    lockScroll();
    errRemove();
}

/* 사용자 팝업 닫기 */
function fnLayerPopupClose(id) {
    $("#" + id).hide();
    unLockScroll();
}

function lockScroll() {
    this.scrollTopPosition = $(window).scrollTop();
    $("#wrapper").css({top: -(this.scrollTopPosition)});
    $("body").addClass('noScroll');
}

function unLockScroll() {
    $("#wrap").css({top: ''});
    $("body").removeClass('noScroll');
    window.scrollTo(0, this.scrollTopPosition);
    window.setTimeout(function () {
        this.scrollTopPosition = null;
    }, 0);
}

function errRemove() {
    $('input').on("propertychange change keyup paste input checked", function () {
        $('.err_text').remove();
    });
}

function errTextElem($input, message) {
    $('.err_text').remove();
    if (!$input.siblings().hasClass('err_text')) {
        const errEl = '<span class="err_text"></span>';
        $input.after(errEl);
    }
    $('.err_text').text(message);
    $input.focus();
}

// 확인/취소 팝업 CM000000_P04
function confirmPopup(message, onConfirm) {
    const $body = $('body');
    let popupElements =
        `<div class="dimmed_black" id="confirmPopupId">
        <div class="popupBox" class="noTitle">
            <p class="popup_message">${message}</p>
            <div class="popup_button_area">
                <a href="javascript:void(0);" class="popup_link cancel">취소</a>
                <a href="javascript:void(0);" class="popup_link confirm">확인</a>
            </div>
            <div class="popup_close"><a href="javascript:void(0);" class="close_button"></a></div>
        </div>
    </div>`;

    if ($('#confirmPopupId').length > 0) {
        $('#confirmPopupId').remove();
    }

    $body.append(popupElements);
    $('#confirmPopupId').show();
    lockScroll();

    let isDelete = false;
    $('#confirmPopupId .confirm').on('click', function () {
        isDelete = true;
        $('#confirmPopupId').hide();
        if (typeof onConfirm === 'function') {
            onConfirm(isDelete);
        }
    });

    $('#confirmPopupId .cancel, #confirmPopupId .close_button').on('click', function () {
        isDelete = false;
        $('#confirmPopupId').hide();
        if (typeof onConfirm === 'function') {
            onConfirm(isDelete);
        }
    });
}

$(document).ready(function () {
    //팝업닫기
    $('.popup_close, .popup_link,.popup_ok').click(function () {
        let thisId = $(this).parents(".dimmed_black").attr("id");
        $("#" + thisId).hide();
        unLockScroll();
    });
});