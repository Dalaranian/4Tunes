window.kakaoAsyncInit function(){
	Kakao.init ('aef00a860346906c88050b79c5211154');
	document.getElementById('kakao_id_login').onclick = kakao_id_login;
	console.log(kakao_id_login);
}
//카카오로그인
function kakao_id_login() {
    window.Kakao.Auth.login({
      success: function (response) {
      console.log(response);
        window.Kakao.API.request({
          url: '/v2/user/me',
          success: function (response) {
        	  console.log(response)
          },
          fail: function (error) {
            console.log(error)
          },
        })
      },
      fail: function (error) {
        console.log(error)
      },
    })
  }
  