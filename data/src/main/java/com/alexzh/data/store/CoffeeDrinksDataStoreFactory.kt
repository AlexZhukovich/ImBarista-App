package com.alexzh.data.store

class CoffeeDrinksDataStoreFactory(
    private val remoteDataStore: CoffeeDrinksRemoteDataStore,
    private val cacheDataStore: CoffeeDrinksCacheDataStore
) {

    fun getDataStore(
        areCoffeeDrinksCached: Boolean,
        isCachedExpired: Boolean
    ): CoffeeDrinksDataStore {
        return if (areCoffeeDrinksCached && !isCachedExpired) {
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