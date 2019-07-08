package com.alexzh.data.store

import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Test

class CoffeesDataStoreFactoryTest {

    private val cacheStore = mockk<CoffeesCacheDataStore>()
    private val remoteStore = mockk<CoffeesRemoteDataStore>()

    private val factory = CoffeesDataStoreFactory(
        remoteStore,
        cacheStore
    )

    @Test
    fun getDataStoreReturnsCachedDataStoreWhenCoffeesAreCachedAndCachedIsNotExpired() {
        val result = factory.getDataStore(
            areCoffeesCached = true,
            isCachedExpired = false
        )
        assertEquals(cacheStore, result)
    }

    @Test
    fun getDataStoreReturnsRemoteDataStoreWhenCoffeesAreCachedAndCachedIsExpired() {
        val result = factory.getDataStore(
            areCoffeesCached = true,
            isCachedExpired = true
        )
        assertEquals(remoteStore, result)
    }

    @Test
    fun getDataStoreReturnsRemoteDataStoreWhenCoffeesAreNotCachedAndCachedIsExpired() {
        val result = factory.getDataStore(
            areCoffeesCached = false,
            isCachedExpired = true
        )
        assertEquals(remoteStore, result)
    }

    @Test
    fun getDataStoreReturnsRemoteDataStoreWhenCoffeesAreNotCachedAndCachedIsNotExpired() {
        val result = factory.getDataStore(
            areCoffeesCached = false,
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