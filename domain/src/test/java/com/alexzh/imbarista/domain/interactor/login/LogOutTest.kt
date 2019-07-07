package com.alexzh.imbarista.domain.interactor.login

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.AuthRepository
import io.mockk.mockk
import org.junit.Test

class LogOutTest {

    private val repository = mockk<AuthRepository>(relaxed = true)
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val logOut = LogOut(
        repository,
        postExecutionThread
    )

    @Test
    fun logOutCompletesSuccessfully() {
        logOut.buildCompletableUseCase()
            .test()
            .assertComplete()
    }
}