document.getElementById("mypage_form").addEventListener("submit", function(event) {
    event.preventDefault();

    var userPw = [[${session.login.user_pw}]];
    var currentPw = document.getElementById("mypage_pw").value;
    if (userPw == currentPw) {
      alert("비밀번호가 맞습니다");
    } else {
      alert("비밀번호가 틀렸습니다");
    }
  });