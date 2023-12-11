package com.sabanci.ovatify.data

data class RecommendationReturn(
    val message : String,
    val tracks_info : ArrayList<RecommendedSong>
)
