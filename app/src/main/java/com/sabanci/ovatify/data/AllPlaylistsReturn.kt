package com.sabanci.ovatify.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class AllPlaylistsReturn(val items:ArrayList<Playlistitem>)


data class Playlistitem(
    val id: String,
    val name: String,
    val description: String,
    val song_imgs: List<String>,
    val user_id: String?,
    val friend_group_id: String?
)
