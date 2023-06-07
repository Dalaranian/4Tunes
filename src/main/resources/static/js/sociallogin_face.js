window.fbAsyncInit = function() {
    FB.init({
      appId: '6473407266107089',
      cookie: true,
      xfbml: true,
      version: 'v17.0'
    });
    FB.AppEvents.logPageView();
  };
  document.getElementById('fb-login-btn').addEventListener('click', function() {
    FB.login(
      function(response) {
        if (response.status === 'connected') {
          FB.api('/me', 'get', { fields: 'name,email' }, function(r) {
            const facebook_email = r.email;
            const facebook_name = r.name;
            console.log(facebook_email);
            console.log(facebook_name);
            window.location.href = "/login/sociallogin?email=" + encodeURIComponent(facebook_email) + "&name=" + encodeURIComponent(facebook_name);
          });
        } else if (response.status === 'not_authorized') {
          alert('로그인해야 이용 가능한 기능입니다.');
        } else {
          alert('페이스북에 로그인해야 이용 가능한 기능입니다.');
        }
      },
      { scope: 'public_profile,email' }
    );
  });
  
  
  
  
   // 수정할 예정
   window.addEventListener('DOMContentLoaded', function() {
   // URL에서 쿼리 파라미터를 가져옴
   const urlParams = new URLSearchParams(window.location.search);
    
   // 이메일과 이름을 가져옴
   const email = urlParams.get('email');
   const name = urlParams.get('name');
    
   // 이메일과 이름 필드에 값을 할당하고 읽기 전용으로 설정
   const emailField = document.getElementById('join-email');
   const nameField = document.getElementById('join-name');
    
   emailField.value = email;
   nameField.value = name;
    
   emailField.setAttribute('readonly', 'readonly');
   nameField.setAttribute('readonly', 'readonly');
});

 
  
  
  
  