package com.sabanci.ovatify.data

data class RecommendedSong(
    val name: String,
    val main_artist: ArrayList<String>,
    val release_year: Int,
    val id: String,
    val img_url: String
)
