package com.sabanci.ovatify.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


@Parcelize
data class LibraryModel (
    val title: String,
    val musicModels: @RawValue ArrayList<MusicModel>

):Parcelable