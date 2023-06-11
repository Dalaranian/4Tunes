Kakao.init('aef00a860346906c88050b79c5211154'); //발급받은 키 중 javascript키를 사용해준다.
// fbLogin 함수 정의
function kakao_id_login() {
    Kakao.Auth.login({
      success: function (response) {
        Kakao.API.request({
          url: '/v2/user/me',
          success: res => {
          		const kakao_account = res.kakao_account
          		const kakao_name = kakao_account.profile.nickname;
				const kakao_email = kakao_account.email;
                window.location.href = "/login/sociallogin?email=" + encodeURIComponent(kakao_email) + 
                										"&name=" + encodeURIComponent(kakao_name);
                console.log(kakao_name);
                console.log(akako_email);
                },
		  fail: function (error) {
		          alert('로그인해야 이용 가능한 기능입니다.');
		          console.log(error)
         		 },
         		 })
         		 },
		  fail: function (error) {
		      	alert('카카오에 로그인해야 이용 가능한 기능입니다.');
		        console.log(error)
			   },
	      })
  }

  