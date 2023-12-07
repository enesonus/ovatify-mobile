package com.sabanci.ovatify.data

data class SongDetails(
    val id: String,
    val name: String,
    val genres: ArrayList<String>,
    val artists: ArrayList<String>,
    val albums: ArrayList<String>,
    val instruments: ArrayList<String>,
    val release_year: Int,
    val duration: Double,
    val tempo: String,
    val mood: String,
    val recorded_environment: String,
    val replay_count: Int,
    val version: String,
    val img_url: String,
    val average_rating: String,
    val user_rating: String
)
