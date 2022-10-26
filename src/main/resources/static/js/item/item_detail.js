//장바구니 버튼 클릭
function addCart(){
	//로그인이 되어 있지 않으면 alert
	
	//innerHTML : 선택한 태그 안에 있는 태그 내용을 그대로 가지고 옴
//	const str = document.querySelector('#test').innerHTML;
	const check_login = document.querySelector('#test').innerText;
	
	if(check_login.trim() == ''){
		alert('로그인 필요');
		return ;
	} //return을 만나면 밑으로 내려가지 않고 끝나기 때문에 else 구문을 쓸 필요가 없다
	
	addCartAjax();
	
	
	//장바구니 등록 기능
	//(ITEM_CODE, CART_AMOUNT)
	//CATE_CODE : 등록시 쿼리로 생성되는 것 / MEMBER_ID : HTML, JAVA에서 로그인한 사람의 정보를 가지고 올 수 있음
	//REG_DATE : 등록시 자동으로 오늘 날짜 들어감 / TOTAL_PRICE : 가지고 가도 되고 안 가지고 가도 됨 쿼리로 해결 가능
	//자바 스크립트에서 submit 실행 방법
	//1. form 태그를 선택한다.
	//2. 선택한 form 태그에서 submit() 함수를 실행시킨다.
	//document.querySelector('#regCartForm').submit();
	
}

//장바구니 등록 시 실행되는 Ajax
function addCartAjax() {
	//ajax start
	$.ajax({
		url: '/cart/regCart', //요청경로
		type: 'post',
		data: {'itemCode' : document.querySelector('#itemCode').value
			, 'cartAmount' : document.querySelector('#cartAmount').value}, //필요한 데이터
		success: function(result) {
			//모달 창 띄우는 소스
			const modal = new bootstrap.Modal('#regCartModal');
			modal.show();

//			result = confirm('장바구니에 상품을 등록했습니다.\n장바구니 페이지로 이동할까요?');
//			
//			if(result){
//				location.href = '/cart/cartList';
//			}
		},
		error: function() {
			alert('실패');
		}
	});
//ajax end
}

//수량 변경 시 총 가격 자동 변경
function changeTotalPrice(){
	const totalPrice = document.querySelector('#totalPriceSpan').value;

	//ajax start
	$.ajax({
		url: '/item/itemDetailAjax', //요청경로
		type: 'post',
		data: {'totalPrice':totalPrice}, //필요한 데이터
		success: function(result) {
			alert('aaa');
		},
		error: function() {
			alert('실패');
		}
	});
//ajax end
}



//상품 상세 보기 페이지에서 구매 버튼 클릭 시
function buy(){
	const buyAmount = document.querySelector('#cartAmount').value;
	
	document.querySelector('#buyAmountInput').value = buyAmount;
	document.querySelector('#buyForm').submit();
}




	