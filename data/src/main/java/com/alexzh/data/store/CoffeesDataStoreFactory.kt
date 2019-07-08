package com.alexzh.data.store

class CoffeesDataStoreFactory(
    private val remoteDataStore: CoffeesRemoteDataStore,
    private val cacheDataStore: CoffeesCacheDataStore
) {

    fun getDataStore(
        areCoffeesCached: Boolean,
        isCachedExpired: Boolean
    ): CoffeesDataStore {
        return if (areCoffeesCached && !isCachedExpired) {
            cacheDataStore
        } else {
            remoteDataStore
        }
    }

    fun getCachedDataStore(): CoffeesDataStore {
        return cacheDataStore
    }

    fun getRemoteDataStore(): CoffeesDataStore {
        return remoteDataStore
    }
}