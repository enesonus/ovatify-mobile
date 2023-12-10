package com.sabanci.ovatify.data

data class UserRequestBody(
    val username: String,
    val email: String?,
    val img_url: String?,
    val data_processing_consent: Boolean,
    val data_sharing_consent: Boolean
)
