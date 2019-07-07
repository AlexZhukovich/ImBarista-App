package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.imbarista.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class CreateAccountTest {

    private val repository = mockk<UserRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val createAccount = CreateAccount(
        repository,
        postExecutionThread
    )

    @Test
    fun createAccountCompletesSuccessfullyWhenParamIsCorrect() {
        val name = "Test"
        val email = "test@test.com"
        val password = "test"
        val token = "12345qwerty"
        val authUser = AuthUser(name, token)
        val param = CreateAccount.Param.forCreatingAccount(
            name,
            email,
            password
        )
        stubCreateAccount(
            name,
            email,
            password,
            Single.just(authUser)
        )

        createAccount.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun createAccountReturnsCorrectDataWhenParamIsCorrect() {
        val name = "Test"
        val email = "test@test.com"
        val password = "test"
        val token = "12345qwerty"
        val authUser = AuthUser(name, token)
        val param = CreateAccount.Param.forCreatingAccount(
            name,
            email,
            password
        )
        stubCreateAccount(
            name,
            email,
            password,
            Single.just(authUser)
        )

        createAccount.buildSingleUseCase(param)
            .test()
            .assertValue(authUser)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createAccountThrowsExceptionWhenParamIsMissing() {
        val name = "Test"
        val email = "test@test.com"
        val password = "test"
        val token = "12345qwerty"
        val authUser = AuthUser(name, token)
        stubCreateAccount(
            name,
            email,
            password,
            Single.just(authUser)
        )

        createAccount.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun createAccountThrowsExceptionWhenParamIsNull() {
        val name = "Test"
        val email = "test@test.com"
        val password = "test"
        val token = "12345qwerty"
        val authUser = AuthUser(name, token)
        stubCreateAccount(
            name,
            email,
            password,
            Single.just(authUser)
        )

        createAccount.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubCreateAccount(
        name: String,
        email: String,
        password: String,
        authSingle: Single<AuthUser>
    ) {
        every { repository.createAccount(name, email, password) } returns authSingle
    }
}