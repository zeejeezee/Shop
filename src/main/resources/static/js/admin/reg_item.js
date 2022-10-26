
//사용, 미사용 라디오 버튼 클릭 시 진행
function changeStatus(cateCode, status){ //status = USE, UNUSE 둘 중 하나
	const result = confirm('카테고리의 상태를 변경하시겠습니까?');
	
	if(result){
		//ajax start
		//페이지 이동 안 하려고 ajax 사용 
		$.ajax({
			url: '/admin/changeStatusAjax', //컨트롤러로 가야 쿼리 실행 가능
			type: 'post',
			data: {'cateStatus': status, 'cateCode': cateCode},
			success: function(result) {
				alert('상태를 변경했습니다.');
				
				//SELECT 박스의 목록을 다시 조회 -> 그래야 변경 후 등록 화면의 SELECT 박스에도 적용이 됨
				selectCategoryListInUseAjax();
				
			},
			error: function() {
				alert('실패');
			}
		});
		//ajax end
	}
	
}

//카테고리 사용 여부 변경 후 실행되는 ajax 함수
//해당 함수가 실행되면 사용 중인 카테고리 목록을 다시 조회
function selectCategoryListInUseAjax(){
	//ajax start
	$.ajax({
		url: '/admin/selectCategoryListInUseAjax', //요청경로
		type: 'post',
		data: {}, //필요한 데이터
		success: function(result) {
			const selectBox = document.querySelector('#cateCode');
			selectBox.innerHTML = '';
			
			let str = '';
			
			for(const categoryInfo of result){
				str += `<option value="${categoryInfo.cateCode}">${categoryInfo.cateName}</option>`;
			}
			
			selectBox.insertAdjacentHTML('beforeend', str);
			
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}