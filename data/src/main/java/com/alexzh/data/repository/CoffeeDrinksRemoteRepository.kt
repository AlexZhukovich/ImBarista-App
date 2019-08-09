package com.alexzh.data.repository

import com.alexzh.data.model.CoffeeDrinkEntity
import io.reactivex.Single

interface CoffeeDrinksRemoteRepository {

    fun getCoffeeDrinks(accessToken: String): Single<List<CoffeeDrinkEntity>>
}