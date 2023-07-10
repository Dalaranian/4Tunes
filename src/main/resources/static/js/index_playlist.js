$(document).ready(function () {
  $.ajax({
    url: "/playlist/getmyplaylist",
    method: "GET",
    success: function (data) {
      console.log(data);
      var playlistsDiv = $("#playlistsContainer");
      $.each(data, function (index, playlist) {
        var playlistDiv = $('<div class="playlist"></div>');

        var a = $("<a></a>")
          .attr(
            "href",
            "/innerpaging/playlist/public?userNo=" + playlist.userNo
          )
          .text(playlist.userName + "님의 플레이리스트")
          .css({ "padding-left": "25px" });
        var img = $("<img>").attr("src", playlist.albumArt);
        var anchorWithImage = $("<a></a>")
          .attr(
            "href",
            "/playlist/gotoplaylistdetail?userNo=" + playlist.userNo
          )
          .append(img);

        playlistDiv.append($("<div></div>").append(anchorWithImage).append(a));

        playlistsDiv.append(playlistDiv);
      });

      const $slider2 = $("#playlistsContainer");
      const $sliderItems2 = $slider2.find(".playlist");
      const itemCount2 = $sliderItems2.length;
      let currentPosition2 = 0;

      $(".playlist_right").on("click", function (e) {
        e.preventDefault();
        $slider2.append($sliderItems2.eq(currentPosition2).detach());
        currentPosition2 = (currentPosition2 + 1) % itemCount2;
      });

      $(".playlist_left").on("click", function (e) {
        e.preventDefault();
        currentPosition2 = (currentPosition2 - 1 + itemCount2) % itemCount2;
        $slider2.prepend($sliderItems2.eq(currentPosition2).detach());
      });
    },
    error: function (data) {
      console.log(data);
    },
  });
});
