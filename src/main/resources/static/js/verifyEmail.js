function verifyEmail() {
  var joinEmail = document.getElementById("join-email").value;
  var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/; // 이메일 형식 확인 정규표현식

  if (emailRegex.test(joinEmail)) {
    $.ajax({
      url: "/login/verifyemailid",
      method: "POST",
      data: {
        joinEmail: joinEmail,
      },
      success: function (response) {
        console.log("success", response);
        var parsedResponse = JSON.parse(response);
        console.log(parsedResponse.status)
        if (parsedResponse.status === "success") {
          console.log("Email verification successful");
          document.getElementById("btn_join").disabled = false;
          alert("사용할 수 있는 Email 입니다. ")
        } else {
          console.log("Invalid email format");
          alert("중복되었습니다");
        }
      },
      error: function (xhr, status, error) {
        console.error("error", error);
      },
    });
  } else {
    alert("잘못된 이메일 형식입니다.");
  }
}
