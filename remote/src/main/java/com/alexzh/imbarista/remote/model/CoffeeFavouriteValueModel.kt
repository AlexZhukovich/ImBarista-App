package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

class CoffeeFavouriteValueModel constructor(isFavourite: Boolean) {
    @SerializedName("favourite") val favourite = if (isFavourite) 'Y' else 'N'
}