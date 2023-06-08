  window.Kakao.init("aef00a860346906c88050b79c5211154");
	function kakao_id_login(){
	    window.Kakao.Auth.login({
	        scope   : 'profile_nickname, account_email',
	        success : function(authObj){
	            console.log(authObj);
	            window.Kakao.API.request({
	                url     : '/v2/user/me', 
	                success : res => {
	                    const kakao_account = res.kakao_account;
	                    console.log(kakao_account);
	                    console.log(kakao_account.profile.nickname);
 	                    let obj = {
	                    		userId : kakao_account.email,
	                    		userName : kakao_account.profile.nickname
	                    };
	                    
 	                   const json_kakao_account = JSON.stringify(obj);
	                   /* 불러온 계정 정보 컨트롤러로 보내기 */
	                     $.ajax({
	                    	 url: '/login/kakao',
		                        type: 'POST',
		                        data: json_kakao_account,
		                        contentType: 'application/json; charset=utf-8',
		                        dataType: 'json',
		                        success: function(res) {
		                   			if(res.result === "true"){
		                   				alert("로그인 성공")
		                   				location.href="/index";
		                   			}else{
		                   				alert(res.userName+"님은 최초로 로그인 하셨음으로, 추가 정보가 필요합니다. ");
		                   				location.href="/kakaoAuth/kakaoJoin?userId="+res.userId+"&userName="+res.userName;
		                   			}
		                        },
		                        error: function(error) {
		                            console.log(error);
		                        }
	                    });
	                }
	            });
	        }
	    });
	}