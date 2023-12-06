package com.sabanci.ovatify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MusicModel (
    val id : String,
    val imageUrl : String,
    val songName : String,
    val artistName : String

):Parcelable
