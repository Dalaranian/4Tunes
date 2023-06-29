const form = document.getElementById("write-form");
    const titleInput = document.getElementById("community-input");
    const titleError = document.getElementById("title-error");

    form.addEventListener("submit", function(event) {
        if (titleInput.value.trim() === "") {
            titleError.style.display = "block";
            event.preventDefault(); // submit 이벤트를 취소하여 폼이 제출되지 않도록 함
        }
    });