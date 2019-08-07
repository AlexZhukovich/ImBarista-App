package com.alexzh.imbarista.remote.model

data class PagingCoffeeDataModel(
    val rows: Long,
    val totalRows: Long,
    val totalPages: Long,
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val data: List<CoffeeDrinkModel>
)