function addToPlaylist(songTitle, songArtist, songLink, songId) {
//    console.log("Song Title:", songTitle);
//    console.log("Song Artist:", songArtist);
//    console.log("Song Link:", songLink);
//    console.log("Song ID:", songId);

    var songDto = {
        songTitle: songTitle,
        songArtist: songArtist,
        songLink: songLink,
        songId: songId
    };

    console.log(songDto);

    $.ajax({
            url: '/playlist/insertmyplaylist',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(songDto),
            success: function(res) {
                alert(res);
            },
            error: function(res) {
                alert(res);
            }
        });
}
