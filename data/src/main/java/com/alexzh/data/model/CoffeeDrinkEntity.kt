package com.alexzh.data.model

data class CoffeeDrinkEntity(
    val id: Long = -1L,
    val name: String = "",
    val description: String = "",
    val ingredients: List<IngredientEntity> = emptyList()
)