  function sendSearchQuery() {
    var queryContent = document.querySelector('.search-input').value;
    window.location.href = "/search/query/" + encodeURIComponent(queryContent);
  }