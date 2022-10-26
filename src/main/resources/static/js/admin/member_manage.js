//회원 이름 클릭시 실행
function getMemberDetail(memberId){
	//ajax start
		$.ajax({
			url: '/admin/selectMemberDetail', //요청경로
			type: 'post',
			data: {'memberId':memberId}, //필요한 데이터
			success: function(result) {
				
				let str  = '';
				str += '<div class="col-12 mb-3">'
				str += '	<h5>'
				str += '	<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-text-left" viewBox="0 0 16 16">'
				str += '		<path fill-rule="evenodd" d="M2 12.5a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h7a.5.5 0 0 1 0 1h-7a.5.5 0 0 1-.5-.5zm0-3a.5.5 0 0 1 .5-.5h11a.5.5 0 0 1 0 1h-11a.5.5 0 0 1-.5-.5z"/>'
				str += '	</svg>'
				str += '	MEMBER DETAIL INFO'
				str += '	</h5>'
				str += '</div>'
				str += '<div class="col-12 mb-3">'
				str += '<table class="table table-hover text-center">'
				str += '	<colgroup>'
				str += '		<col width="30%">'
				str += '		<col width="70%">'
				str += '	</colgroup>'
				str += '	<tr>                                                   '
				str += '		<th scope="col">ID</th>                            '
				str += `		<td class="col">${result.memberId}</td>            `
				str += '	</tr>                                                  '
				str += '	<tr>                                                   '
				str += '		<th scope="col">NAME</th>                          '
				str += `		<td class="col">${result.memberName}</td>          `
				str += '	</tr>                                                  '
				str += '	<tr>                                                   '
				str += '		<th scope="col">ADDR</th>                          '
				str += `		<td class="col">${result.memberAddr}</td>          `
				str += '	</tr>                                                  '
				str += '	<tr>                                                   '
				str += '		<th scope="col">EMAIL</th>                         '
				str += `		<td class="col">${result.memberEmail}</td>         `
				str += '	</tr>                                                  '
				str += '	<tr>                                                   '
				str += '		<th scope="col">ROLE</th>                          '
				str += `		<td class="col">${result.memberRole}</td>          `
				str += '	</tr>                                                  '
				str += '	<tr>                                                   '
				str += '		<th scope="col">STATUS</th>                        '
				str += `		<td class="col">${result.memberStatus}</td>        `
				str += '	</tr>                                                  '
				str += '</table>                                                   '
				str += '</div>                                                     '
				                                                                   
				
				detailDiv.innerHTML = '';
				document.querySelector('#detailDiv').insertAdjacentHTML('beforeend', str);
				


			},
			error: function() {
				alert('실패');
			}
		});
	//ajax end
}
















