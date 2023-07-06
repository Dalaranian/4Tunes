// 게시글 삭제 버튼 클릭 시 확인 창 표시
document.getElementById("delete-btn").addEventListener("click",
					function(event) {
						event.preventDefault(); // 폼 제출 이벤트 중지

						if (confirm("정말로 삭제하시겠습니까?")) {
							// 확인 버튼을 눌렀을 때 폼을 제출
							document.getElementById("delete-form").submit();
						}
					});	
					
	    // 댓글 삭제 버튼 클릭 시 확인 창 표시		
	    const deleteButtons = document.querySelectorAll('.comment-delete');

	    deleteButtons.forEach(button => {
	        button.addEventListener('click', function(event) {
	            var confirmDelete = confirm("정말로 삭제하시겠습니까?");
	            if (!confirmDelete) {
	                event.preventDefault(); // 삭제를 취소하고 페이지 이동을 막음
	            }
	        });
	    });	
					
	
    
    
      
    
    
      
    