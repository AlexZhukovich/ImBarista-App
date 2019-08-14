package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

data class CoffeeDrinkFavouriteValueModel(
    val isFavourite: Boolean
) {
    @SerializedName("favourite") val favourite = if (isFavourite) 'Y' else 'N'
}