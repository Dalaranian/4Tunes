var pageNo = 0;
var pageSize = 8;

$(document).ready(function() {
    // YouTube 동영상의 플레이어 컨트롤을 제거
    $("iframe").each(function() {
        var src = $(this).attr('src');
        if (src.includes('youtube')) {
            if (src.includes('?')) {
                $(this).attr('src', src + '&controls=0');
            } else {
                $(this).attr('src', src + '?controls=0');
            }
        }
    });

    // 스크롤이 페이지 끝에 도달했을 때 추가 데이터를 불러오기
    $('#main').scroll(function() {
    	 if ($('#main').scrollTop() > $('#main')[0].scrollHeight - $('#main').height() - 200) {
            var request = {
                pageNo : pageNo,
                pageSize : pageSize,
                userNo : [[${param.userNo}]]
            }
            console.log(request);
            $.ajax({
                url: '/playlist/getmoresong',
                type: 'GET',
                contentType:'application.json',
                data: JSON.stringfy(request),
                success: function(data){
                    var songs = data.songs;
                    for (var i = 0; i < songs.length; i++) {
                        var song = songs[i];
                        //추가되는 데이터 형식
                        var newSongItem = `
                            <div class="list_wrapper">
                                <div id="${(i % 2 == 0) ? 'list_left' : 'list_right'}">
                                    <div id="video">
                                        <div class="video-container">
                                            <iframe src="${song.songLink}" frameborder="0"
                                                    allowfullscreen></iframe>
                                        </div>
                                        <div id="context">
                                            <span class="song_title">${song.songTitle}</span>
                                            <br/>
                                            <span class="song_singer">${song.songArtist}</span>
                                            <br/>
                                            <div style="width:200px;">
                                                <button id="plus" style="display: inline-block; padding:0 5px;"
                                                        onclick="deleteToPlaylist('${song.songTitle}', '${song.songArtist}', '${song.songLink}', '${song.songId}', '${song.songAlbumArt}')">
                                                    <img id="plusimg" src="/resources/img/minus.png">
                                                </button>
                                                <div style="display: inline-block;">내 플레이리스트에 제거</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        `;
                        $("#MyPlayList").append(newSongItem);
                    }
                    if(songs.length > 0) {
                        pageNo += 1;
                    } else {
                        // 더 이상 불러올 노래가 없다면 스크롤 이벤트 리스너를 제거
                    	$(window).off('scroll', onScroll);
                    }
                },
                error: function(err){
                    console.log('Error:', err);
                }
            });
        }
    });
});
