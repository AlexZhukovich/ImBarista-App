package com.alexzh.data.store

class CoffeesDataStoreFactory(
    private val remoteDataStore: CoffeeDrinksRemoteDataStore,
    private val cacheDataStore: CoffeeDrinksCacheDataStore
) {

    fun getDataStore(
        areCoffeesCached: Boolean,
        isCachedExpired: Boolean
    ): CoffeeDrinksDataStore {
        return if (areCoffeesCached && !isCachedExpired) {
            cacheDataStore
        } else {
            remoteDataStore
        }
    }

    fun getCachedDataStore(): CoffeeDrinksDataStore {
        return cacheDataStore
    }

    fun getRemoteDataStore(): CoffeeDrinksDataStore {
        return remoteDataStore
    }
}