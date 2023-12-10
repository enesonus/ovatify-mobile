package com.sabanci.ovatify.data

data class UserInfo(val id:String,val name:String,val img_url:String, val preferences:UserPreferences)
data class UserPreferences(val data_processing: Boolean, val data_sharing:Boolean)
