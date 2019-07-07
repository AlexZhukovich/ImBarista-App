package com.alexzh.imbarista.domain.interactor.login

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.imbarista.domain.repository.AuthRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test
import java.lang.IllegalArgumentException

class LogInTest {

    private val repository = mockk<AuthRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val logIn = LogIn(
        repository,
        postExecutionThread
    )

    @Test
    fun logInCompletesSuccessfullyWhenParamsAreCorrect() {
        val email = "test@test.com"
        val password = "test"
        val name = "Test"
        val token = "12345qwerty"
        val authUser = AuthUser(name, token)
        val param = LogIn.Param.forLogIn(email, password)
        stubLogIn(email, password, Single.just(authUser))

        logIn.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun logInReturnsCorrectDataWhenParamsAreCorrect() {
        val email = "test@test.com"
        val password = "test"
        val name = "Test"
        val token = "12345qwerty"
        val authUser = AuthUser(name, token)
        val param = LogIn.Param.forLogIn(email, password)
        stubLogIn(email, password, Single.just(authUser))

        logIn.buildSingleUseCase(param)
            .test()
            .assertValue(authUser)
    }

    @Test(expected = IllegalArgumentException::class)
    fun logInThrowsExceptionWhenParamIsMissing() {
        val email = "test@test.com"
        val password = "test"
        val name = "Test"
        val token = "12345qwerty"
        val authUser = AuthUser(name, token)
        stubLogIn(email, password, Single.just(authUser))

        logIn.buildSingleUseCase()
            .test()
            .assertValue(authUser)
    }

    @Test(expected = IllegalArgumentException::class)
    fun logInThrowsExceptionWhenParamIsNull() {
        val email = "test@test.com"
        val password = "test"
        val name = "Test"
        val token = "12345qwerty"
        val authUser = AuthUser(name, token)
        stubLogIn(email, password, Single.just(authUser))

        logIn.buildSingleUseCase(null)
            .test()
            .assertValue(authUser)
    }

    private fun stubLogIn(
        email: String,
        password: String,
        authSingle: Single<AuthUser>
    ) {
        every { repository.logIn(email, password) } returns authSingle
    }
}