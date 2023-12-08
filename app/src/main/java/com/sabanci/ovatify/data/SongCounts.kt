package com.sabanci.ovatify.data

data class SongCounts(val song_counts:ArrayList<DateandCount>)
data class DateandCount(val date:String,val count: Int)
