function deleteAlert(user_no) {
  if (confirm("정말 해당 회원을 탈퇴 처리하시겠습니까?")) {
    alert(user_no + " 번 회원 탈퇴되었습니다. ");
    location.href = "/adminpage/delete/" + user_no;
  }
}
