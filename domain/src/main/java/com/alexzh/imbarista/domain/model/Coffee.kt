package com.alexzh.imbarista.domain.model

data class Coffee(
    val id: Long = -1L,
    val name: String = "",
    val description: String = "",
    val ingredients: List<Ingredient> = emptyList()
)