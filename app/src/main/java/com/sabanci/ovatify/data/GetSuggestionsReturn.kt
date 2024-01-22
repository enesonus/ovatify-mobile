package com.sabanci.ovatify.data

data class GetSuggestionsReturn(val items: List<SuggestionItem>)
data class SuggestionItem(
    val id: String,
    val suggester_name: String,
    val suggester_img_url: String,
    val song_id: String,
    val song_img_url: String,
    val song_name: String
)




