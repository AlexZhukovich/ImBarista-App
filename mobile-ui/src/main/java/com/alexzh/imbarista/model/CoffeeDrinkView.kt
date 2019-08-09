package com.alexzh.imbarista.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CoffeeDrinkView(
    val id: Long = -1L,
    val name: String = "",
    val imageUrl: String ="",
    val description: String = "",
    val ingredients: String = "",
    val isFavourite: Boolean = false
) : Parcelable