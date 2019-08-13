package com.alexzh.imbarista.remote

import com.alexzh.data.model.CoffeeDrinkEntity
import com.alexzh.imbarista.remote.mapper.CoffeeMapper
import com.alexzh.imbarista.remote.model.CoffeeDrinkDataModel
import com.alexzh.imbarista.remote.model.CoffeeDrinkModel
import com.alexzh.imbarista.remote.model.ResponseModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateCoffeeEntities
import com.alexzh.testdata.remote.GenerateRemoteTestData
import com.alexzh.testdata.remote.GenerateRemoteTestData.generateCoffeeDrinkDataModel
import com.alexzh.testdata.remote.GenerateRemoteTestData.generateCoffeeDrinkModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class CoffeeDrinkRemoteRepositoryImplTest {

    private val service = mockk<CoffeeDrinksService>()
    private val mapper = mockk<CoffeeMapper>()

    private val repository = CoffeeDrinkRemoteRepositoryImpl(service, mapper)

    @Test
    fun getCoffeeDrinksThrowsException() {
        val accessToken = randomString()
        val response: ResponseModel<CoffeeDrinkDataModel> = ResponseModel(data = generateCoffeeDrinkDataModel())

        val coffeeDrinkEntities = generateCoffeeEntities()

        stubMapFromModelList(response.data.data.first(), coffeeDrinkEntities.first())
        stubGetCoffeeDrinksFromService(accessToken, Single.just(response))
        stubGetCoffeeDrinks(accessToken, Single.just(coffeeDrinkEntities))

        repository.getCoffeeDrinks(accessToken)
            .test()
            .assertValue(coffeeDrinkEntities)
    }

    private fun stubGetCoffeeDrinksFromService(
        accessToken: String,
        single: Single<ResponseModel<CoffeeDrinkDataModel>>
    ) {
        every { service.getCoffeeDrinks(accessToken) } returns single
    }

    private fun stubMapFromModelList(model: CoffeeDrinkModel, entity: CoffeeDrinkEntity) {
        every { mapper.mapFromModel(model) } returns entity
    }

    private fun stubGetCoffeeDrinks(accessToken: String, single: Single<List<CoffeeDrinkEntity>>) {
        every { repository.getCoffeeDrinks(accessToken) } returns single
    }
}