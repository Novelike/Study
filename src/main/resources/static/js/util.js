function strCheck(str, type) {
	var REGEX = {
		EMAIL: /\S+@\S+\.\S+/,
		PWD_RULE: /^(?=.*[a-zA-Z])((?=.*\d)(?=.*\W)).{8,20}$/,
		NAME_RULE: /^[가-힣a-zA-Z]+$/,
		ID_RULE: /^[a-z]+[a-z0-9]{7,15}$/g
	};

	if (type === "email") {
		return REGEX.EMAIL.test(str);
	} else if (type === "pwd") {
		return REGEX.PWD_RULE.test(str);
	} else if (type === "name") {
		return REGEX.NAME_RULE.test(str);
	} else if (type === "id") {
		return REGEX.ID_RULE.test(str);
	} else {
		return false;
	}
}

/**
 * 한글포함 문자열 길이를 구한다
 */
function getTextLength(str) {
	var len = 0;
	for (var i = 0; i < str.length; i++) {
		if (escape(str.charAt(i)).length == 6) {
			len++;
		}
		len++;
	}
	return len;
}

const autoHyphen = (target) => {
	target.value = target.value
		.replace(/[^0-9]/, '')
		.replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

//전화번호 하이픈 삭제
function phoneHyphenRomve(val) {
	return val.replace(/-/g, "");
}

//전화번호 하이픈 생성
function phoneFormatter(num, type) {

	var formatNum = '';
	try {
		if (num.length == 11) {
			if (type == 0) {
				formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-****-$3');
			} else {
				formatNum = num.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
			}
		} else if (num.length == 8) {
			formatNum = num.replace(/(\d{4})(\d{4})/, '$1-$2');
		} else if (num.length == 9) {
			if (num.indexOf('02') == 0) {
				if (type == 0) {
						formatNum = num.replace(/(\d{2})(\d{3})(\d{4})/, '$1-***-$3');
				} else {
					formatNum = num.replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
				}
			}
		}else {
			if (num.indexOf('02') == 0) {
				
				if (type == 0) {
					formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-****-$3');
				} else {
					
					formatNum = num.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
				}
			} else {
				if (type == 0) {
					formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-***-$3');
				} else {
					formatNum = num.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
				}
			}
		}
	} catch (e) {
		formatNum = num;
	}
	return formatNum;
}

//3자리수마다 콤마찍기
function comma(str) {
	if (str == null) return str;
	str = str.toString();
	str = String(str);
	return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
}

// 콤마 풀기
function uncomma(str) {
	if (str == null || str == "") return str;
	str = str.toString();
	var s = str.split(".");
	str = String(s[0]);

	if (s.length > 1) {
		return str.replace(/[^\d]+/g, '').toString() + "." + s[1].toString();
	}
	else {
		return str.replace(/[^\d]+/g, '').toString();
	}

}

//날짜 유효성 체크 (윤달 포함)
function validDate(vDate) {
	var vValue = vDate;
	var vValue_Num = vValue.replace(/[^0-9]/g, ""); //숫자를 제외한 나머지는 예외처리 합니다.

	//8자리가 아닌 경우 false
	if (vValue_Num.length != 8) {
		return false;
	}

	//8자리의 yyyymmdd를 원본 , 4자리 , 2자리 , 2자리로 변경해 주기 위한 패턴생성을 합니다.
	var rxDatePattern = /^(\d{4})(\d{1,2})(\d{1,2})$/;
	var dtArray = vValue_Num.match(rxDatePattern);

	if (dtArray == null) {
		return false;
	}

	//0번째는 원본 , 1번째는 yyyy(년) , 2번재는 mm(월) , 3번재는 dd(일) 입니다.
	dtYear = dtArray[1];
	dtMonth = dtArray[2];
	dtDay = dtArray[3];

	//yyyymmdd 체크
	if (dtMonth < 1 || dtMonth > 12) {
		return false;
	}
	else if (dtDay < 1 || dtDay > 31) {
		return false;
	}
	else if ((dtMonth == 4 || dtMonth == 6 || dtMonth == 9 || dtMonth == 11) && dtDay == 31) {
		return false;
	}
	else if (dtMonth == 2) {
		var isleap = (dtYear % 4 == 0 && (dtYear % 100 != 0 || dtYear % 400 == 0));
		if (dtDay > 29 || (dtDay == 29 && !isleap)) {
			return false;
		}
	}

	return true;
}

function checkCorporateRegistrationNumber(value) {
	var valueMap = value.replace(/-/gi, '').split('').map(function(item) {
		return parseInt(item, 10);
	});

	if (valueMap.length === 10) {
		var multiply = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5);
		var checkSum = 0;

		for (var i = 0; i < multiply.length; ++i) {
			checkSum += multiply[i] * valueMap[i];
		}

		checkSum += parseInt((multiply[8] * valueMap[8]) / 10, 10);
		return Math.floor(valueMap[9]) === (10 - (checkSum % 10));
	}

	return false;
}

function formatDate(date) {
	var d = new Date(date),
		month = '' + (d.getMonth() + 1),
		day = '' + d.getDate(),
		year = d.getFullYear();
	if (month.length < 2)
		month = '0' + month;

	if (day.length < 2)
		day = '0' + day;

	return [year, month, day].join('-');
}

function getYearMonth(date) {
	let year = date.getFullYear();
	let month = date.getMonth() + 1; // JavaScript의 Date 객체는 0부터 시작하는 월을 반환하므로 1을 더합니다.
	if (month < 10) {
		month = '0' + month; // 월이 한 자릿수일 경우 앞에 0을 붙입니다.
	}
	return year + "-" + month;
}

//캡락 여부 확인
function checkCapsLock(event) {
	if (event.getModifierState("CapsLock")) {
		$(".error_alert").html("Caps Lock이 켜져 있습니다.");
		
	} else {
		$(".error_alert").html("");
	}
}

// 특수문자 입력 방지
function characterCheck(obj){
	var regExp = /^[ㄱ-ㅎ|가-힣|a-z|A-Z]+$/;
	// 허용할 특수문자는 여기서 삭제하면 됨
	// 지금은 띄어쓰기도 특수문자 처리됨 참고하셈
	if( !regExp.test(obj.value) ){
		
		obj.value = obj.value.substring( 0 , obj.value.length - 1 ); // 입력한 특수문자 한자리 지움
	}
}

// NULL 이거나 공백 확인
function isEmptyString(str) {
	return !str || str.length === 0;
}

// 휴대폰 번호 유효성 확인
// 유효하면 true 아니면 false
function isValidPhoneNumber(phoneNumber) {
	const regex = /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
	return regex.test(phoneNumber);
}

// 비동기 POST 방식으로 데이터 전송
async function asyncPostData(url = '', data = {}) {
	const response = await fetch(url, {
		method: 'POST',
		headers: {
			    'Content-Type': 'application/json'
			  , 'X-Requested-With': 'XMLHttpRequest'
		},
		body: JSON.stringify(data), // 서버로 전송할 데이터를 JSON 형식으로 변환
	}).then((res) => {
		if (res.status === 403) {
			location.href = '/login/login';
			alert('로그인이 필요합니다.');
			return;
		}
		return res;
	}).catch((error) => {
		console.error('Error:', error);
	});
	return response; // 응답을 JSON 형식으로 파싱
}

$(document).ready(function() {
	$('.password_input i').on('click', function() {
		$('.password_input input').toggleClass('active');
		if ($('.password_input input').hasClass('active')) {
			$(this).attr('class', "fa-eye-slash").prev('input').prop('type', "text");
		} else {
			$(this).attr('class', "fa-eye").prev('input').prop('type', 'password');
		}
	});
});

$(document).on("focus", "input:text[koreanCurrency]", function()	{	
	$(this).val( $(this).val().replace("원", ""));
});

$(document).on("focusout", "input:text[koreanCurrency]", function()	{
	$(this).val( $(this).val().replace(",","") );
	$(this).val( $(this).val().replace(/[^-\.0-9]/gi,"") );
	$(this).val( $(this).val().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,") );
	if($(this).val() != '' ) {
		$(this).val( $(this).val()+'원');
	}		
});