package com.alexzh.imbarista.remote

import com.alexzh.imbarista.remote.mapper.CoffeeMapper
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.mockk.mockk
import org.junit.Test
import java.lang.UnsupportedOperationException

class CoffeeDrinkRemoteRepositoryImplTest {

    private val service = mockk<CoffeeDrinksService>()
    private val mapper = mockk<CoffeeMapper>()

    private val repository = CoffeeDrinkRemoteRepositoryImpl(service, mapper)

    @Test
    fun getCoffeesThrowsException() {
        repository.getCoffees()
            .test()
            .assertError(UnsupportedOperationException::class.java)
    }
}