/////////////////////////////////////////////////////////////
//-------- 스크립트 실행과 동시에 필요한 변수 생성 --------//
/////////////////////////////////////////////////////////////

//제목 행 체크박스
const checkAll = document.querySelector('#checkAll');

//제목 행을 제외한 모든 체크박스
const chks = document.querySelectorAll('.chk');

//모달 창
const updateAmountModal = new bootstrap.Modal('#updateAmountModal');



/////////////////////////////////////////////////////////////
//-------------------- 함수 정의 영역 ---------------------//
/////////////////////////////////////////////////////////////

//총 가격을 세팅하는 함수
function setFinalPrice(){
	//체크된 체크박스를 선택
	const checkedBoxes = document.querySelectorAll('.chk:checked');
	
	//체크된 체크박스 내용물의 합계
	//값이 변하기 때문에 const가 아닌 let 사용
	let finalPrice = 0;
	
	//체크박스를 기준으로 체크박스를 감싸고 있는 가장 가까운 tr 안에서
	//class가 totalPriceDiv인 것의 내용물을 찾음
	for(const checkedBox of checkedBoxes) {
		//들고온 것(문자)을 숫자로 변경해 줘야 함
		//data-total-price를 가지고 오기 때문에 숫자로 변경 가능
		//innerText로 가지고 왔으면 문자열이 ₩10,000 형태로 인식되기 때문에 숫자로 변경 불가능
		const totalPrice = parseInt(checkedBox.closest('tr').querySelector('.totalPriceDiv').dataset.totalPrice);
		finalPrice = finalPrice + totalPrice;
	}
	
	//id가 finalPriceSpan인 것에 접근 후 내용물을 finalPrice로 바꿔 줌
	document.querySelector('#finalPriceSpan').innerText = '₩' + finalPrice.toLocaleString();
}


//변경 버튼 클릭 시 실행
function goUpdateAmount(selectedTag){
	updateAmountModal.show();
	
	//수량
	//선택한 버튼을 기준으로 가장 가까운 row라는 클래스에 찾아가서 그 안에 있는 input type이 number인 것에 접근 후 value 값을 가지고 옴
	const cartAmount = selectedTag.closest('.row').querySelector('input[type="number"]').value;
	//cartCode
	const cartCode = selectedTag.closest('tr').querySelector('input[type="checkbox"]').value;
	
	//변경 버튼을 클릭하면 cartCode 값을 가지게 된다(checkbox에서 가지고 오기 때문에)
	
	//클릭한 버튼의 data-cart-code, data-cart-amount 속성의 값을 수량을 변경하고자 하는 데이터로 세팅
	//모달 창에 있는 확인 버튼에 접근해야 함 -> 확인 버튼에 id 속성을 줌
	document.querySelector('#updateAmountBtn').dataset.cartCode = cartCode; //dataset에서 cartCode라는 이름으로 만들어진 속성에 접근해서 java라는 데이터를 넣어 줌
	document.querySelector('#updateAmountBtn').dataset.cartAmount = cartAmount;
	
	//기존 장바구니 수량을 취소 버튼에 전달
	//1. 수량 input 태그의 originAmount 값을 저장함
	//2. 취소 버튼의 data-origin-amount 속성에 그 값을 넣어 줌
	//2-1. 취소 버튼을 선택하기 위해 id 속성을 준다
	const originAmount = selectedTag.closest('.row').querySelector('input[type="number"]').dataset.originAmount;
	document.querySelector("#cancelBtn").dataset.originAmount = originAmount;
	
	//확인 버튼을 누르면 변경 버튼의 data-origin-amount 값을 변경한 값으로 바꿔 줘야 한다
	//바꾸지 않으면 처음 값이 그대로 들어 있기 때문에 변경 후 재변경할 때 취소를 누르면 처음 들어간 값이 들어감
	
	
}

//수량 버튼 클릭 후 나타나는 모달창의 확인 버튼 클릭 시 실행
function updateAmount(){
	const cartCode = document.querySelector('#updateAmountBtn').dataset.cartCode;
	const cartAmount = document.querySelector('#updateAmountBtn').dataset.cartAmount;
	
	//ajax start
	$.ajax({
		url: '/cart/updateAmount', //요청경로
		type: 'post',
		data: {'cartCode': cartCode, 'cartAmount' : cartAmount}, //필요한 데이터
		success: function(result) {
			alert('성공');
			updateAmountModal.hide();
			
			//장바구니 상품에 대한 수량 및 가격 갱신
			for(const chk of chks){
				if(chk.value == cartCode){
					chk.closest('tr').querySelector('input[type="number"]').dataset.originAmount = cartAmount;
				}
			}

		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}


//수량 변경 팝업에서 취소 버튼 클릭 시
function rollbackAmount(){
	//모든 체크박스를 돌면서 cartcode 값을 가지고 온다
		//체크박스의 value 값에 cartCode가 들어가 있다
	//실제로 수량을 롤백시켜야 하는 cartCode랑 비교해서 일치하면 그 행에 있는 수량을 롤백시키면 된다
	
	//실제로 변경 버튼이 눌러졌을 때 선택된 cartCode
	const selectedCartCode = document.querySelector('#updateAmountBtn').dataset.cartCode;
	//원본 수량 값
	//원본 수량은 변경 버튼 클릭 시 취소 버튼에 전달하도록 만들어 둠 -> 취소 버튼을 찾아감
	const originAmount = document.querySelector('#cancelBtn').dataset.originAmount;
	
	for(const chk of chks){
		if(chk.value == selectedCartCode){
			//수량을 원래대로 돌려야 하는 input 태그를 선택해서 value 값에 원본 수량 값을 넣어 준다
			chk.closest('tr').querySelector('input[type="number"]').value = originAmount;
		}
	}
	
}

//선택삭제 및 선택구매 버튼 클릭 시
function deleteOrBuy(selectedTag){ //매개변수 this를 selectedTag로 받는다
	const deleteForm = document.querySelector('#cartForm');
	
	//체크한 cartCode를 다 가지고 온다
	const checkedChks = document.querySelectorAll('.chk:checked');
	
	if(checkedChks.length == 0){
		alert('상품을 먼저 선택하세요');
		return ;
	}
	
	let cartCodes = '';
	
	for(const checkedChk of checkedChks){
		cartCodes = cartCodes + checkedChk.value + ',';	
	}//"CART_001,CART_002,CART_003"
	
	deleteForm.querySelector('input').value = cartCodes;
	
	if(selectedTag.innerText == '선택삭제'){ //클릭한 태그 안의 내용 / 자바스크립트는 문자열에 equals X
		deleteForm.action = '/cart/deleteCart';		
	}
	else {
		deleteForm.action = '/buy/buyInfo';
	}
	
	
	deleteForm.submit();
	
}






/////////////////////////////////////////////////////////////
//------------------- 이벤트 정의 영역 --------------------//
/////////////////////////////////////////////////////////////

//전체선택, 전체해제 이벤트
checkAll.addEventListener('click', function(){
	//제목 행 체크박스 체크 여부
	const isChecked = checkAll.checked; //체크박스가 해제되면 false, 체크되면 true
	//장바구니 목록의 모든 체크박스 선택
	const checkboxes = document.querySelectorAll('.chk');
	
	if(isChecked) { //제목 행의 체크박스가 체크되었다면
		for(const checkBox of checkboxes){
			checkBox.checked = true;
		}
	}
	
	else{ //체크되지 않았다면
		for(const checkBox of checkboxes){
			checkBox.checked = false;
		}
	}
	
	setFinalPrice();
	
});


//장바구니 목록에 있는 체크박스 클릭 시 진행
for(const chk of chks){
	chk.addEventListener('click', function(){
		setFinalPrice();
	});
}

