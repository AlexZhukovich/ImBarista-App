package com.alexzh.data.store

import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeeDrinksDataStoreFactoryTest {

    private val cacheStore = mockk<CoffeeDrinksCacheDataStore>()
    private val remoteStore = mockk<CoffeeDrinksRemoteDataStore>()

    private val factory = CoffeeDrinksDataStoreFactory(
        remoteStore,
        cacheStore
    )

    @Test
    fun getDataStoreReturnsCachedDataStoreWhenCoffeeDrinksAreCachedAndCachedIsNotExpired() {
        val result = factory.getDataStore(
            areCoffeeDrinksCached = true,
            isCachedExpired = false
        )
        assertEquals(cacheStore, result)
    }

    @Test
    fun getDataStoreReturnsRemoteDataStoreWhenCoffeeDrinksAreCachedAndCachedIsExpired() {
        val result = factory.getDataStore(
            areCoffeeDrinksCached = true,
            isCachedExpired = true
        )
        assertEquals(remoteStore, result)
    }

    @Test
    fun getDataStoreReturnsRemoteDataStoreWhenCoffeeDrinksAreNotCachedAndCachedIsExpired() {
        val result = factory.getDataStore(
            areCoffeeDrinksCached = false,
            isCachedExpired = true
        )
        assertEquals(remoteStore, result)
    }

    @Test
    fun getDataStoreReturnsRemoteDataStoreWhenCoffeeDrinksAreNotCachedAndCachedIsNotExpired() {
        val result = factory.getDataStore(
            areCoffeeDrinksCached = false,
            isCachedExpired = false
        )
        assertEquals(remoteStore, result)
    }

    @Test
    fun getCachedDataStoreReturnsCorrectStore() {
        assertEquals(cacheStore, factory.getCachedDataStore())
    }

    @Test
    fun getRemoteDataStoreReturnsCorrectStore() {
        assertEquals(remoteStore, factory.getRemoteDataStore())
    }
}