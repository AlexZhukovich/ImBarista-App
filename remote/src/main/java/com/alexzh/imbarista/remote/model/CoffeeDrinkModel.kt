package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

data class CoffeeDrinkModel(
    @SerializedName("id") val id: Long = -1L,
    @SerializedName("title") val name: String = "",
    @SerializedName("photo_url") val imageUrl: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("ingredients") val ingredients: String = "",
    @SerializedName("favourite") val isFavourite: String = ""
) {
    fun isCoffeeDrinkFaviorite(): Boolean {
        return isFavourite == "Y"
    }
}