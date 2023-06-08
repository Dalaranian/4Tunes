Kakao.init('aef00a860346906c88050b79c5211154'); //발급받은 키 중 javascript키를 사용해준다.
console.log(Kakao.isInitialized()); // sdk초기화여부판단
//카카오로그인
function kakao_id_login() {
    Kakao.Auth.login({
      success: function (response) {
        Kakao.API.request({
          url: '/v2/user/me',
          success: function(response) {
					//console.log("성공resopnse");
					//console.log(response)
					/*const email = response.kakao_account.email;*/
					const kakao_name = response.properties.nickname;
					const kakao_email = (response.kakao_account.kakao_email != undefined ? response.kakao_account.kakao_email : '');
					console.log(name); 
					console.log(email);
					window.location.href = "/login/sociallogin?email=" + encodeURIComponent(kakao_email) + "&name=" + encodeURIComponent(kakao_name);
	
				},
          fail: function (error) {
          alert('로그인해야 이용 가능한 기능입니다.');
            console.log(error)
          },
        })
      },
      fail: function (error) {
      alert('zkzkdh에 로그인해야 이용 가능한 기능입니다.');
        console.log(error)
      },
    })
  }