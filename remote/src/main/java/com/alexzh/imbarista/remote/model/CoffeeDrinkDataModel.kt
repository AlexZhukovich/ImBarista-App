package com.alexzh.imbarista.remote.model

import com.google.gson.annotations.SerializedName

data class CoffeeDrinkDataModel(
    @SerializedName("row_returned") val rows: Long,
    @SerializedName("coffee-drinks") val data: List<CoffeeDrinkModel>
)