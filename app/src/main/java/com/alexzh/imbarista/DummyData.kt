package com.alexzh.imbarista

object DummyData {

    fun getCoffees() : List<Coffee> = mutableListOf(
        Coffee(
            id = 1,
            name = "Americano",
            photoUrl = "",
            description = "Americano desc",
            isFavourite = true,
            ingredients = listOf(
                Ingredient("Americano A"),
                Ingredient("Americano B"),
                Ingredient("Americano C"))),
        Coffee(
            id = 2,
            name = "Latte",
            photoUrl = "",
            description = "Latte desc",
            isFavourite = true,
            ingredients = listOf(
                Ingredient("Latte A"),
                Ingredient("Latte B"),
                Ingredient("Latte C"))),
        Coffee(
            id = 3,
            name = "Cappuccino",
            photoUrl = "",
            description = "Cappuccino desc",
            isFavourite = true,
            ingredients = listOf(
                Ingredient("Cappuccino A"),
                Ingredient("Cappuccino B"),
                Ingredient("Cappuccino C"))),
        Coffee(
            id = 4,
            name = "Espresso",
            photoUrl = "",
            description = "Espresso desc",
            isFavourite = true,
            ingredients = listOf(
                Ingredient("Espresso A"),
                Ingredient("Espresso B"),
                Ingredient("Espresso C"))),
        Coffee(
            id = 5,
            name = "Espresso Macchiato",
            photoUrl = "",
            description = "Espresso Macchiato desc",
            isFavourite = false,
            ingredients = listOf(
                Ingredient("Espresso Macchiato A"),
                Ingredient("Espresso Macchiato B"),
                Ingredient("Espresso Macchiato C"))),
        Coffee(
            id = 6,
            name = "Latte Macchiato",
            photoUrl = "",
            description = "Latte Macchiato desc",
            isFavourite = false,
            ingredients = listOf(
                Ingredient("Latte Macchiato A"),
                Ingredient("Latte Macchiato B"),
                Ingredient("Latte Macchiato C"))),
        Coffee(
            id = 7,
            name = "Mocha",
            photoUrl = "",
            description = "Mocha desc",
            isFavourite = false,
            ingredients = listOf(
                Ingredient("Mocha A"),
                Ingredient("Mocha B"),
                Ingredient("Mocha C")))
    )

    data class Coffee(
        val id: Long,
        val name: String,
        val photoUrl: String,
        val description: String,
        val isFavourite: Boolean,
        val ingredients: List<Ingredient>
    )

    data class Ingredient(
        val name: String
    )
}