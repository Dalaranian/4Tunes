$(function() {
document.getElementById("delete-btn").addEventListener("click",
					function(event) {
						event.preventDefault(); // 폼 제출 이벤트 중지

						if (confirm("정말로 삭제하시겠습니까?")) {
							// 확인 버튼을 눌렀을 때 폼을 제출
							document.getElementById("delete-form").submit();
						}
					});
});

function deleteComment(commentNo){
    if(confirm("정말로 삭제하시겠습니까?")){
        location.href="/community/delete/comment/"+commentNo;
    }
}

	
    
    
      
    
    
      
    