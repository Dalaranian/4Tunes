$(function(){
    $('#joinBtn').click(function(){
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
    });

	$('#loginBtn').click(function(){
		alert("로그인이 필요합니다 !!");
		location.href='/nav/login';
	});
});