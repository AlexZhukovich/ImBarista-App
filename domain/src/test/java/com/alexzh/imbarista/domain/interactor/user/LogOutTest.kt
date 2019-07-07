package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.UserRepository
import io.mockk.mockk
import org.junit.Test

class LogOutTest {

    private val repository = mockk<UserRepository>(relaxed = true)
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