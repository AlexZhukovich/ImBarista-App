package com.alexzh.data.repository

import com.alexzh.data.model.CoffeeEntity
import io.reactivex.Single

interface CoffeesRepositoryRemote {

    fun getCoffees(): Single<List<CoffeeEntity>>
}