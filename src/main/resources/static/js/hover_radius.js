$(document).ready(function () {
  $(".today").hover(
    function () {
      $(this)
        .find("img")
        .css("border-radius", "70% 30% 65% 35% / 27% 64% 36% 73% ");
    },
    function () {
      $(this)
        .find("img")
        .css("border-radius", "50% 50% 36% 64% / 55% 44% 56% 45%");
    }
  );
});
