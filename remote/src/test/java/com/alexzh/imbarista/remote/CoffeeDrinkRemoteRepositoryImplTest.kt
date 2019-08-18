package com.alexzh.imbarista.remote

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.remote.mapper.CoffeeMapper
import com.alexzh.imbarista.remote.model.CoffeeDrinkDataModel
import com.alexzh.imbarista.remote.model.CoffeeDrinkFavouriteValueModel
import com.alexzh.imbarista.remote.model.CoffeeDrinkModel
import com.alexzh.imbarista.remote.model.ResponseModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntities
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntity
import com.alexzh.imbarista.commonandroidtestdata.remote.GenerateRemoteTestData.generateCoffeeDrinkDataModel
import com.alexzh.imbarista.remote.mapper.HttpExceptionMapper
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class CoffeeDrinkRemoteRepositoryImplTest {

    private val service = mockk<CoffeeDrinksService>()
    private val coffeeMapper = mockk<CoffeeMapper>()
    private val httpExceptionMapper = mockk<HttpExceptionMapper>()

    private val repository = CoffeeDrinkRemoteRepositoryImpl(service, coffeeMapper, httpExceptionMapper)

    @Test
    fun getCoffeeDrinksCompletesSuccessfully() {
        val accessToken = randomString()
        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())

        val coffeeDrinkEntities = generateCoffeeEntities()

        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntities.first())
        stubGetCoffeeDrinksFromService(accessToken, Single.just(response))

        repository.getCoffeeDrinks(accessToken)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeDrinksReturnsCorrectData() {
        val accessToken = randomString()
        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())
        val coffeeDrinkEntities = generateCoffeeEntities()

        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntities.first())
        stubGetCoffeeDrinksFromService(accessToken, Single.just(response))

        repository.getCoffeeDrinks(accessToken)
            .test()
            .assertValue(coffeeDrinkEntities)
    }

    @Test
    fun getCoffeeByIdCompletesSuccessfully() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()

        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())
        val coffeeDrinkEntities = generateCoffeeEntities()

        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntities.first())
        stubGetCoffeeDrinkByIdFromService(coffeeDrinkId, accessToken, Single.just(response))

        repository.getCoffeeById(coffeeDrinkId, accessToken)
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeeByIdReturnsCorrectData() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()

        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())
        val coffeeDrinkEntity = generateCoffeeEntity()

        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntity)
        stubGetCoffeeDrinkByIdFromService(coffeeDrinkId, accessToken, Single.just(response))

        repository.getCoffeeById(coffeeDrinkId, accessToken)
            .test()
            .assertValue(coffeeDrinkEntity)
    }

    @Test
    fun addCoffeeDrinkToFavouriteCompletesSuccessfully() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()

        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())
        val coffeeDrinkEntity = generateCoffeeEntity()
        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntity)
        stubAddCoffeeDrinkToFavourite(coffeeDrinkId, accessToken, Single.just(response))

        repository.addCoffeeDrinkToFavourite(coffeeDrinkId, accessToken)
            .test()
            .assertComplete()
    }

    @Test
    fun addCoffeeDrinkToFavouriteReturnsCorrectData() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()

        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())
        val coffeeDrinkEntity = generateCoffeeEntity()
        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntity)
        stubAddCoffeeDrinkToFavourite(coffeeDrinkId, accessToken, Single.just(response))

        repository.addCoffeeDrinkToFavourite(coffeeDrinkId, accessToken)
            .test()
            .assertComplete()
    }

    @Test
    fun removeCoffeeDrinkFromFavouritesCompletesSuccessfully() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()

        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())
        val coffeeDrinkEntity = generateCoffeeEntity()
        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntity)
        stubRemoveCoffeeDrinkFromFavourites(coffeeDrinkId, accessToken, Single.just(response))

        repository.removeCoffeeDrinkFromFavourite(coffeeDrinkId, accessToken)
            .test()
            .assertComplete()
    }

    @Test
    fun removeCoffeeDrinkFromFavouritesReturnsCorrectData() {
        val coffeeDrinkId = randomLong()
        val accessToken = randomString()

        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())
        val coffeeDrinkEntity = generateCoffeeEntity()
        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntity)
        stubRemoveCoffeeDrinkFromFavourites(coffeeDrinkId, accessToken, Single.just(response))

        repository.removeCoffeeDrinkFromFavourite(coffeeDrinkId, accessToken)
            .test()
            .assertComplete()
    }

    private fun stubGetCoffeeDrinksFromService(
        accessToken: String,
        single: Single<ResponseModel<CoffeeDrinkDataModel>>
    ) {
        every { service.getCoffeeDrinks(accessToken) } returns single
    }

    private fun stubGetCoffeeDrinkByIdFromService(
        coffeeDrinkId: Long,
        accessToken: String,
        single: Single<ResponseModel<CoffeeDrinkDataModel>>
    ) {
        every { service.getCoffeeDrinkById(coffeeDrinkId, accessToken) } returns single
    }

    private fun stubAddCoffeeDrinkToFavourite(
        coffeeDrinkId: Long,
        accessToken: String,
        single: Single<ResponseModel<CoffeeDrinkDataModel>>
    ) {
        every { service.markDrinkAsFavourite(
            coffeeDrinkId,
            accessToken,
            CoffeeDrinkFavouriteValueModel(true))
        } returns single
    }

    private fun stubRemoveCoffeeDrinkFromFavourites(
        coffeeDrinkId: Long,
        accessToken: String,
        single: Single<ResponseModel<CoffeeDrinkDataModel>>
    ) {
        every { service.markDrinkAsFavourite(
            coffeeDrinkId,
            accessToken,
            CoffeeDrinkFavouriteValueModel(false))
        } returns single
    }

    private fun stubMapFromModelList(
        model: CoffeeDrinkModel,
        entity: CoffeeDrinkEntity
    ) {
        every { coffeeMapper.mapFromModel(model) } returns entity
    }
}