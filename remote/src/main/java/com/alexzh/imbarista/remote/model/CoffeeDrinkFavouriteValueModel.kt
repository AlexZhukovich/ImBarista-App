package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

class CoffeeDrinkFavouriteValueModel constructor(isFavourite: Boolean) {
    @SerializedName("favourite") val favourite = if (isFavourite) 'Y' else 'N'
}