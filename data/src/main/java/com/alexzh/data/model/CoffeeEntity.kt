package com.alexzh.data.model

data class CoffeeEntity(
    val id: Long = -1L,
    val name: String = "",
    val description: String = "",
    val ingredients: List<IngredientEntity> = emptyList()
)