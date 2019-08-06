package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

data class RefreshTokenModel(
    @SerializedName("refresh_token") val refreshToken: String
)