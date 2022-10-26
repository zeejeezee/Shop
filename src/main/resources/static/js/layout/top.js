////////////////////////////////////////////////////
//------ 페이지 열림과 동시에 실행되는 코드 ------//
////////////////////////////////////////////////////
isLoginFail();



////////////////////////////////////////////////////
//--------------------- 변수 ---------------------//
////////////////////////////////////////////////////

//회원가입 모달
const join_modal = document.querySelector('#join_modal');
//로그인 모달
const login_modal = document.querySelector('#login_modal');



////////////////////////////////////////////////////
//------------------ 함수 정의 -------------------//
////////////////////////////////////////////////////

//회원가입에서 search 버튼 클릭시 진행
function searchAddr(){
	new daum.Postcode({
        oncomplete: function(data) {
			//도로명 주소
        	const roadAddr = data.roadAddress;
            document.querySelector('#memberAddr').value = roadAddr;
        }
	}).open();
}

//로그인 기능 함수
//ajax가 실행되면 컨트롤러로 간다
function login(){
	const member_id = login_modal.querySelector('#memberId').value;
	const member_pw = login_modal.querySelector('#memberPw').value;
	
	//ajax start
	$.ajax({
		url: '/member/ajaxLogin', //요청경로
		type: 'post',
		data: {'memberId':member_id, 'memberPw':member_pw}, //필요한 데이터
		success: function(result) {
			if(result) { //만약에 result가 true라면
				alert('로그인 성공');
				
				//모달창을 닫고 상단의 [join login]을 '이름'님 반갑습니다. logout]으로 바꿔 주기
				//페이지 이동
				location.href = '/item/list';				
			}
			else{
				alert('로그인 실패');
			}	
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}

//로그인 실패 여부로 모달창을 띄워 주는 함수
function isLoginFail(){
	//자바스크립트에서 isLoginFail 데이터를 가지고 와야 함
	const isLoginFail = document.querySelector('#isLoginFail').value;
	
	if(isLoginFail == 'true') { //isLoginFail이 true라면
		const myModalAlternative = new bootstrap.Modal('#login_modal'); //id가 login_modal인 태그(top.html-login에 시작되는 div 태그 id) 선택
		myModalAlternative.show();
		//강제로 모달을 닫는 코드: myModalAlternative.hide();
	}
}


/////////////////////////////////////////////////////
//------------------ 이벤트 정의 ------------------//
/////////////////////////////////////////////////////

//회원가입 모달이 닫히면 실행되는 이벤트
join_modal.addEventListener('hidden.bs.modal', function(event){
	/*//join_modal에 있는 input 태그 중 type이 button인 것만 빼고 다 가지고 옴
	const inputs = join_modal.querySelectorAll('input:not([type="button"])');
	//모든 input 태그의 value 값을 빈값으로 만들어 준다
	for(const inputTag of inputs){
		inputTag.value = '';
	} */

	join_modal.querySelector('form').reset();

});

//로그인 모달이 닫히면 실행되는 이벤트
login_modal.addEventListener('hidden.bs.modal', function(event){
	login_modal.querySelector('form').reset(); //login_modal 안에 있는 form 태그를 찾아서 input 태그 다 reset
});



