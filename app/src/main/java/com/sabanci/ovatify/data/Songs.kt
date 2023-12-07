package com.sabanci.ovatify.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Songs(
    val id: String,
    val name: String,
    val release_year: String,
    val main_artist: String,
    val img_url: String
):Parcelable
