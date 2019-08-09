package com.alexzh.data.model

data class CoffeeDrinkEntity(
    val id: Long = -1L,
    val name: String = "",
    val imageUrl: String = "",
    val description: String = "",
    val ingredients: String = "",
    val isFavourite: Boolean = false
)