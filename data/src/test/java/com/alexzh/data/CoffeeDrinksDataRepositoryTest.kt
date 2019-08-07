package com.alexzh.data

import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.model.CoffeeEntity
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.data.store.CoffeesDataStoreFactory
import com.alexzh.data.store.CoffeeDrinksRemoteDataStore
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCoffee
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Test

class CoffeeDrinksDataRepositoryTest {

    private val mapper = mockk<CoffeeMapper>()
    private val storeFactory = mockk<CoffeesDataStoreFactory>()
    private val cacheRepository = mockk<CoffeeDrinksCacheRepository>()
    private val remoteDataStore = mockk<CoffeeDrinksRemoteDataStore>()

    private val dataRepository = CoffeeDrinksDataRepository(
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
    fun getCoffeesByIdCompletesSuccessfully() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffee()

        stubGetRemoteDataStore()
        stubGetCoffeeFromRemoteStoreById(coffeeEntity.id, Single.just(coffeeEntity))
        stubMapFromEntity(coffeeEntity, coffee)

        dataRepository.getCoffeesById(randomLong())
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesByIdReturnsCorrectData() {
        val coffeeEntity = generateCoffeeEntity()
        val coffee = generateCoffee()

        stubGetRemoteDataStore()
        stubGetCoffeeFromRemoteStoreById(coffeeEntity.id, Single.just(coffeeEntity))
        stubMapFromEntity(coffeeEntity, coffee)

        dataRepository.getCoffeesById(randomLong())
            .test()
            .assertValue(coffee)
    }

    @Test
    fun addCoffeeToFavouritesCompletesSuccessfully() {
        val coffeeId = randomLong()

        stubGetRemoteDataStore()
        stubAddCoffeeToFavourites(coffeeId, Completable.complete())

        dataRepository.addCoffeeToFavourites(coffeeId)
            .test()
            .assertComplete()
    }

    @Test
    fun removeCoffeeFromFavouritesCompletesSuccessfully() {
        val coffeeId = randomLong()

        stubGetRemoteDataStore()
        stubRemoveCoffeeFromFavourites(coffeeId, Completable.complete())

        dataRepository.removeCoffeeFromFavourites(randomLong())
            .test()
            .assertComplete()
    }

    private fun stubGetRemoteDataStore() {
        every { storeFactory.getRemoteDataStore() } returns remoteDataStore
    }

    private fun stubGetCoffeesFromRemoteStore(coffeesSingle: Single<List<CoffeeEntity>>) {
        every { remoteDataStore.getCoffees() } returns coffeesSingle
    }

    private fun stubGetCoffeeFromRemoteStoreById(coffeeId: Long, coffeeSingle: Single<CoffeeEntity>) {
        every { remoteDataStore.getCoffeeById(coffeeId) } returns coffeeSingle
    }

    private fun stubMapFromEntity(
        coffeeEntities: CoffeeEntity,
        coffee: Coffee
    ) {
        every { mapper.mapFromEntity(coffeeEntities) } returns coffee
    }

    private fun stubAddCoffeeToFavourites(
        coffeeId: Long,
        completable: Completable
    ) {
        every { remoteDataStore.setCoffeeAsFavourite(coffeeId) } returns completable
    }

    private fun stubRemoveCoffeeFromFavourites(
        coffeeId: Long,
        completable: Completable
    ) {
        every { remoteDataStore.setCoffeeAsNotFavourite(coffeeId) } returns completable
    }
}