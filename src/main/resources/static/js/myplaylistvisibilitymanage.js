function manageVisibility(isVisible){
    fetch('/playlist/managevisibility', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: isVisible
    })
    .then(response => {
      // 응답 처리
      console.log('요청이 성공적으로 전송되었습니다.');
    })
    .catch(error => {
      // 오류 처리
      console.error('요청 전송 중 오류가 발생했습니다.', error);
    });
}