package com.alexzh.imbarista.remote.model

data class SessionModel(
    val sessionId: Long = -1L,
    val accessToken: String = "",
    val accessTokenExpiry: Long = -1L,
    val refreshToken: String = "",
    val refreshTokenExpiry: Long = -1L
)