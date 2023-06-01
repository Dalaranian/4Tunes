$(function(){
    $('#joinBtn').click(function(){
    	let signIn = "${signIn}";
    	if(signIn == ""){
    		alert("로그인이 필요한 서비스입니다.");
    		location.href="/nav/login";
    	} else {
    		$.ajax({
	            url:'/jq/kakaopay',
	            dataType:'json',
	            success:function(data){
	            	var box = data.next_redirect_pc_url;
	           		window.open(box);
	            },
	            error:function(error){
	                alert(error);
	            }
	        });
    	}
    });
});