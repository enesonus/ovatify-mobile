package com.sabanci.ovatify.data

data class PlaylistByIdReturn(val playlist:PlaylistObject)

data class PlaylistObject(
    val id:String,
    val name:String,
    val description:String,
    val songs :ArrayList<Songs>,
    val user_id:String,
    val friend_group_id:String
)
