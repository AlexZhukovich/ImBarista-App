package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

class UserModel(
    @SerializedName("user_id") val id: Long = -1,
    @SerializedName("fullname") val name: String = "",
    @SerializedName("email") val email: String = "",
    @SerializedName("password") val password: String = ""
)