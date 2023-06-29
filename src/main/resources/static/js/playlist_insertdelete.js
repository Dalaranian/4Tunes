function addToPlaylist(songTitle, songArtist, songLink, songId, songAlbumArt) {
//    console.log("Song Title:", songTitle);
//    console.log("Song Artist:", songArtist);
//    console.log("Song Link:", songLink);
//    console.log("Song ID:", songId);

    var songDto = {
        songTitle       : songTitle,
        songArtist      : songArtist,
        songLink        : songLink,
        songId          : songId,
        songAlbumArt    : songAlbumArt
    };

    console.log(songDto);

    $.ajax({
            url: '/playlist/insertmyplaylist',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(songDto),
            success: function(res) {
                console.log(typeof res);
                if(res.indexOf("<html lang=") != -1) {
                    location.href="/nav/login";
                }else{
                    alert(res);
                }
            },
            error: function(res) {
                alert(res);
            }
        });
}

function deleteToPlaylist(songTitle, songArtist, songLink, songId, songAlbumArt){
    var songDto = {
        songTitle       : songTitle,
        songArtist      : songArtist,
        songLink        : songLink,
        songId          : songId,
        songAlbumArt    : songAlbumArt
    };
    $.ajax({
        url: '/playlist/deletemyplaylist',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify(songDto),
        success:function(res){
            alert(res);
            window.location.reload();
        },
        error:function(res){
            console.log(res);
        }
    });
}

