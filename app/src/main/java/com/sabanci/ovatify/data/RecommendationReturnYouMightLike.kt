package com.sabanci.ovatify.data

data class RecommendationReturnYouMightLike(

    val message : String,
    val tracks_info : ArrayList<RecommendationSongYouMightLike>
)
