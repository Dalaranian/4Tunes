window.Kakao.init("aef00a860346906c88050b79c5211154");
document.getElementById('kakao_id_login').onclick = kakao_id_login;
function kakao_id_login(){
	   	window.Kakao.Auth.login({
	        success : function(response){
	            console.log(response);
	            window.Kakao.API.request({
	                url     : '/v2/user/me', 
	                success : function(response) {
	               		console.log(response);
	                    const kakao_account = kakao_account;
	                    const userId : kakao_account.email,
	                    const userName : kakao_account.profile.name
	                    console.log(kakao_account);
	                    console.log(kakao_account.profile.name);
	                    window.location.href = "/login/sociallogin?email=" + encodeURIComponent(userId) + 
	                    						"&name=" + encodeURIComponent(userName);
	                }
	            });
	        }
	    });
	}