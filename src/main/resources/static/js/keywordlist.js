$(document).ready(function() {
  const searchKeyword = $("#search-keyword");
  const kwSelect = $("#kw");
  const readonlyInput = $("#readonly");

  searchKeyword.on("input", function() {
    const inputValue = searchKeyword.val().toLowerCase();
    // 사용자가 영어 소문자를 칠 수 있을 거라고 가정
    kwSelect.find("option").each(function() {
      const optionValue = $(this).val().toLowerCase();
      const optionText = $(this).text().toLowerCase();
      // value값이 같은 게 포함될 때, text값이 같은 게 포함될 때 두 경우 모두 보여줌.
      if (optionValue.includes(inputValue) || optionText.includes(inputValue)) {
        $(this).show();
      } else {
        $(this).hide();
      }
    });

    readonlyInput.val().split(",").map(function(item) {
      const trimmedItem = item.trim();
      kwSelect.find("option").filter(function() {
        return $(this).text().toLowerCase() === trimmedItem.toLowerCase();
      }).hide();
    });
  });

  // 옵션 더블 클릭 시 #readonly에 표시
  kwSelect.on("dblclick", function() {
    const selectedOptions = kwSelect.find("option:selected").map(function() {
      return $(this).text();
    }).get();

    readonlyInput.val(function(index, value) {
      const allOptions = value ? value.split(", ") : [];
      return allOptions.concat(selectedOptions).join(", ");
    });

    kwSelect.find("option:selected").hide();
  });
});
