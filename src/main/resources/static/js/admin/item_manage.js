//수량 재고 변경 버튼 클릭 시 실행
function updateStock(itemCode, selectedTag){
	//const itemStock = selectedTag.parentElement.previousElementSibling.children[0].vaule;
	const itemStock = selectedTag.closest('td').querySelector('.stockInput').value;
	
	//ajax start
	$.ajax({
		url: '/admin/updateStock', //요청경로
		type: 'post',
		data: {'itemCode' : itemCode, 'itemStock' : itemStock}, //필요한 데이터
		success: function(result) {
			//모달 창 띄우는 소스
			const modal = new bootstrap.Modal('#updateStockModal');
			modal.show();
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}

//판매중, 매진 라디오 버튼 클릭 시 실행
function changeItemStatus(itemCode, status){
	const result = confirm('상품의 상태를 변경하시겠습니까?');
	
	if(result){
		//ajax start
		//페이지 이동 안 하려고 ajax 사용 
		$.ajax({
			url: '/admin/changeItemStatus', //컨트롤러로 가야 쿼리 실행 가능
			type: 'post',
			data: {'itemStatus': status, 'itemCode': itemCode},
			success: function(result) {
				alert('상태를 변경했습니다.');
			},
			error: function() {
				alert('실패');
			}
		});
		//ajax end
	}
}
