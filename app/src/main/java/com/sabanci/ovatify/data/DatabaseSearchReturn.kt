package com.sabanci.ovatify.data

data class DatabaseSearchReturn( val message: String,
                                 val songs_info: List<DatabaseSong>)
data class DatabaseSong(
    val spotify_id: String,
    val track_name: String,
    val release_year: Int,
    val album_name: List<String>,
    val artist: List<String>,
    val album_url: String
)