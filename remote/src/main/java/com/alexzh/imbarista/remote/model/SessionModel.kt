package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

data class SessionModel(
    @SerializedName("session_id") val sessionId: Long = -1L,
    @SerializedName("access_token") val accessToken: String = "",
    @SerializedName("access_token_expires_in") val accessTokenExpiry: Long = -1L,
    @SerializedName("refresh_token") val refreshToken: String = "",
    @SerializedName("refresh_token_expires_in") val refreshTokenExpiry: Long = -1L
)