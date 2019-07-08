package com.alexzh.imbarista.domain.interactor.coffee.browse

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.Coffee
import com.alexzh.imbarista.domain.repository.CoffeesRepository
import com.alexzh.testdata.domain.GenerateDomainTestData
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetCoffeesTest {

    private val repository = mockk<CoffeesRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCoffees = GetCoffees(
        repository,
        postExecutionThread
    )

    @Test
    fun getCoffeesCompletesSuccessfully() {
        val coffees = GenerateDomainTestData.generateCoffees()
        stubGetCoffees(Single.just(coffees))

        getCoffees.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test
    fun getCoffeesReturnsCorrectData() {
        val coffees = GenerateDomainTestData.generateCoffees()
        stubGetCoffees(Single.just(coffees))

        getCoffees.buildSingleUseCase()
            .test()
            .assertValue(coffees)
    }

    private fun stubGetCoffees(single: Single<List<Coffee>>) {
        every { repository.getCoffees() } returns single
    }
}