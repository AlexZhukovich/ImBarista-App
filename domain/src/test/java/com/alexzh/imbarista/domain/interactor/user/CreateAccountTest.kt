package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.testdata.base.RandomData.randomEmail
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.domain.GenerateDomainTestData.generateCreateAccountParam
import com.alexzh.testdata.domain.GenerateDomainTestData.generateUser
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
        val user = generateUser()
        val param = generateCreateAccountParam(user.name)
        stubCreateAccount(
            name = param.name,
            email = param.email,
            password = param.password,
            userSingle = Single.just(user)
        )

        createAccount.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun createAccountReturnsCorrectDataWhenParamIsCorrect() {
        val user = generateUser()
        val param = generateCreateAccountParam(user.name)
        stubCreateAccount(
            name = param.name,
            email = param.email,
            password = param.password,
            userSingle = Single.just(user)
        )

        createAccount.buildSingleUseCase(param)
            .test()
            .assertValue(user)
    }

    @Test(expected = IllegalArgumentException::class)
    fun createAccountThrowsExceptionWhenParamIsMissing() {
        val user = generateUser()
        stubCreateAccount(
            name = user.name,
            email = randomEmail(),
            password = randomString(),
            userSingle = Single.just(user)
        )

        createAccount.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun createAccountThrowsExceptionWhenParamIsNull() {
        val user = generateUser()
        stubCreateAccount(
            name = user.name,
            email = randomEmail(),
            password = randomString(),
            userSingle = Single.just(user)
        )

        createAccount.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubCreateAccount(
        name: String,
        email: String,
        password: String,
        userSingle: Single<User>
    ) {
        every { repository.createAccount(name, email, password) } returns userSingle
    }
}