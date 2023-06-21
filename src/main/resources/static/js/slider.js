$(document).ready(function () {
  const $slider = $(".todays");
  const $sliderItems = $slider.find(".today");
  const itemWidth = $sliderItems.first().outerWidth(true); // margin 포함한 너비 사용
  const itemCount = $sliderItems.length;
  const visibleItems = 4; // 화면에 보여질 아이템 개수
  let currentPosition = 0;

  $(".btn .right").on("click", function (e) {
    e.preventDefault();

    // 맨 처음 아이템을 맨 뒤로 이동
    $slider.append($sliderItems.eq(currentPosition).detach());
    currentPosition = (currentPosition + 1) % itemCount; // 다음 아이템 인덱스로 갱신
  });

  $(".btn .left").on("click", function (e) {
    e.preventDefault();

    // 맨 뒤의 아이템을 맨 앞으로 이동
    currentPosition = (currentPosition - 1 + itemCount) % itemCount; // 이전 아이템 인덱스로 갱신
    $slider.prepend($sliderItems.eq(currentPosition).detach());
  });
});
