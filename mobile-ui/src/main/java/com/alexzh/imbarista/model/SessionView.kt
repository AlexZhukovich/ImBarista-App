package com.alexzh.imbarista.model

data class SessionView(
    val sessionId: Long = -1L,
    val accessToken: String = "",
    val accessTokenExpiry: Long = -1L,
    val refreshToken: String = "",
    val refreshTokenExpiry: Long = -1L
) {
    fun isExistingSessionValid(): Boolean {
        return sessionId != -1L &&
            accessToken.isNotBlank() &&
            accessTokenExpiry != -1L &&
            refreshToken.isNotBlank() &&
            refreshTokenExpiry != -1L
    }
}