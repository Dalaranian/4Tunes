function manageVisibility(isVisible){
    fetch('/playlist/managevisibility', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: isVisible
    })
    .then(response => response.text())
    .then(data => {
      // 응답 처리
      alert(data);
    })
    .catch(error => {
      // 오류 처리
      console.error('요청 전송 중 오류가 발생했습니다.', error);
    });
}