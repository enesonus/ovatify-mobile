package com.sabanci.ovatify.data

import com.google.gson.annotations.SerializedName

data class CreateUserRequest(
    @SerializedName("userToken")
    val userToken: String?,

    @SerializedName("userEmail")
    val email: String?
)
