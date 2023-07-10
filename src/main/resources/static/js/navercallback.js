var naver_id_login = new naver_id_login(
  "_Bz0QIIPqcH1PJ3NCi5I",
  "http://ec2-52-78-2-172.ap-northeast-2.compute.amazonaws.com/login/join"
);
var state = naver_id_login.getUniqState();
naver_id_login.setButton("green", 1, 0);
naver_id_login.setDomain("http://ec2-52-78-2-172.ap-northeast-2.compute.amazonaws.com");
naver_id_login.setState(state);
naver_id_login.setPopup();
naver_id_login.init_naver_id_login();
