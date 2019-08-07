package com.alexzh.imbarista.domain.model

data class CoffeeDrink(
    val id: Long = -1L,
    val name: String = "",
    val description: String = "",
    val ingredients: List<Ingredient> = emptyList()
)