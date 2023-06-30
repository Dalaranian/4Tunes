function insertSong(songTitle, songArtist, songLink, songId, songAlbumArt) {

    var songDto = {
        songTitle       : songTitle,
        songArtist      : songArtist,
        songLink        : songLink,
        songId          : songId,
        songAlbumArt    : songAlbumArt
    };
    
    console.log(songDto);
    
    var playlist = $("#playlist option:selected").val();
	console.log(playlist);
	
	var insertInfo = {
		"songDto" : JSON.stringify(songDto),
		"playlist" : playlist
	}
	
	$.ajax({
            url: '/adminpage/insertsong',
            type: 'GET',
            contentType: 'application/json',
            data: insertInfo,
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
