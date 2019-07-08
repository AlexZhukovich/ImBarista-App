package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.testdata.base.RandomData.randomEmail
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.domain.GenerateDomainTestData.generateAuthUser
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCreateAccountParam
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
        val authUser = generateAuthUser()
        val param = generateCreateAccountParam(authUser.name)
        stubCreateAccount(
            param.name,
            param.email,
            param.password,
            Single.just(authUser)
        )

        createAccount.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun createAccountReturnsCorrectDataWhenParamIsCorrect() {
        val authUser = generateAuthUser()
        val param = generateCreateAccountParam(authUser.name)
        stubCreateAccount(
            param.name,
            param.email,
            param.password,
            Single.just(authUser)
        )

        createAccount.buildSingleUseCase(param)
            .test()
            .assertValue(authUser)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createAccountThrowsExceptionWhenParamIsMissing() {
        val authUser = generateAuthUser()
        stubCreateAccount(
            name = authUser.name,
            email = randomEmail(),
            password = randomString(),
            authSingle = Single.just(authUser)
        )

        createAccount.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun createAccountThrowsExceptionWhenParamIsNull() {
        val authUser = generateAuthUser()
        stubCreateAccount(
            name = authUser.name,
            email = randomEmail(),
            password = randomString(),
            authSingle = Single.just(authUser)
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