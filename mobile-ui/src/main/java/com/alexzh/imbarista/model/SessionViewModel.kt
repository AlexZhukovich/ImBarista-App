package com.alexzh.imbarista.model

data class SessionViewModel(
    val sessionId: Long = -1L,
    val accessToken: String = "",
    val accessTokenExpiry: Long = -1L,
    val refreshToken: String = "",
    val refreshTokenExpiry: Long = -1L
)