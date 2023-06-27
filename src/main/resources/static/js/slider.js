$(document).ready(function() {
    $(".today_right").click(function() {
        var firstCard = $(".today .today_card:first");
        $(".today").append(firstCard);
    });

    $(".today_left").click(function() {
        var lastCard = $(".today .today_card:last");
        $(".today").prepend(lastCard);
    });
});