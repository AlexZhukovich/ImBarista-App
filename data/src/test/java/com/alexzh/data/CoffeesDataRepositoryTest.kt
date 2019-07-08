package com.alexzh.data

import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.model.CoffeeEntity
import com.alexzh.data.repository.CoffeesCacheRepository
import com.alexzh.data.store.CoffeesDataStoreFactory
import com.alexzh.data.store.CoffeesRemoteDataStore
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffee
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class CoffeesDataRepositoryTest {

    private val mapper = mockk<CoffeeMapper>()
    private val storeFactory = mockk<CoffeesDataStoreFactory>()
    private val cacheRepository = mockk<CoffeesCacheRepository>()
    private val remoteDataStore = mockk<CoffeesRemoteDataStore>()

    private val dataRepository = CoffeesDataRepository(
        mapper,
        cacheRepository,
        storeFactory
    )

    @Test
    fun getCoffeesCompletesSuccessfully() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffee()

        stubGetRemoteDataStore()
        stubGetCoffeesFromRemoteStore(Single.just(listOf(coffeeEntity)))
        stubMapFromEntity(coffeeEntity, coffee)

        dataRepository.getCoffees()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesReturnsCorrectData() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffee()
        val expectedCoffees = listOf(coffee)

        stubGetRemoteDataStore()
        stubGetCoffeesFromRemoteStore(Single.just(listOf(coffeeEntity)))
        stubMapFromEntity(coffeeEntity, coffee)

        dataRepository.getCoffees()
            .test()
            .assertValue(expectedCoffees)
    }

    @Test
    fun getCoffeesByNameThrowsException() {
        dataRepository.getCoffeesByName(randomString())
            .test()
            .assertError(java.lang.UnsupportedOperationException::class.java)
    }

    @Test
    fun getCoffeesByIdThrowsException() {
        dataRepository.getCoffeesById(randomLong())
            .test()
            .assertError(java.lang.UnsupportedOperationException::class.java)
    }

    @Test
    fun addCoffeeToFavouritesThrowsException() {
        dataRepository.addCoffeeToFavourites(randomLong())
            .test()
            .assertError(java.lang.UnsupportedOperationException::class.java)
    }

    @Test
    fun removeCoffeeFromFavouritesThrowsException() {
        dataRepository.removeCoffeeFromFavourites(randomLong())
            .test()
            .assertError(java.lang.UnsupportedOperationException::class.java)
    }

    private fun stubGetRemoteDataStore() {
        every { storeFactory.getRemoteDataStore() } returns remoteDataStore
    }

    private fun stubGetCoffeesFromRemoteStore(coffeesSingle: Single<List<CoffeeEntity>>) {
        every { remoteDataStore.getCoffees() } returns coffeesSingle
    }

    private fun stubMapFromEntity(
        coffeeEntities: CoffeeEntity,
        coffee: Coffee
    ) {
        every { mapper.mapFromEntity(coffeeEntities) } returns coffee
    }
}