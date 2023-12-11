package com.sabanci.ovatify.data

data class RecommendationSinceYouLikeReturn(
    val message: String,
    val tracks_info: Map<String, ArrayList<RecommendationSongYouMightLike>>
)


