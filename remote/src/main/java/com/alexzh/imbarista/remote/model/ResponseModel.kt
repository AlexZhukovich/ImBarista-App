package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

data class ResponseModel<T>(
    @SerializedName("statusCode") val statusCode: Int = -1,
    @SerializedName("success") val success: Boolean = false,
    @SerializedName("messages") val messages: List<String> = listOf(),
    @SerializedName("data") val data: T
)