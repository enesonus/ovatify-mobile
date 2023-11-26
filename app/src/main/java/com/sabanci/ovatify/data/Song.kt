package com.sabanci.ovatify.data

import android.media.Image

data class Song(val viewType : Int)
{
    var oneSong : OneSong? = null

    constructor(viewType: Int, oneSong: OneSong) : this(viewType)
    {
        this.oneSong = oneSong
    }
}



data class OneSong(val image : Int, val songName : String, val artist : String)