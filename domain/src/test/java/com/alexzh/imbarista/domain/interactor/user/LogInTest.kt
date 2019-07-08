package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.testdata.domain.GenerateDomainTestData.generateAuthUser
import com.alexzh.testdata.domain.GenerateDomainTestData.generateLogInParam
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test
import java.lang.IllegalArgumentException

class LogInTest {

    private val repository = mockk<UserRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val logIn = LogIn(
        repository,
        postExecutionThread
    )

    @Test
    fun logInCompletesSuccessfullyWhenParamsAreCorrect() {
        val authUser = generateAuthUser()
        val param = generateLogInParam()
        stubLogIn(param.email, param.password, Single.just(authUser))

        logIn.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun logInReturnsCorrectDataWhenParamsAreCorrect() {
        val authUser = generateAuthUser()
        val param = generateLogInParam()
        stubLogIn(param.email, param.password, Single.just(authUser))

        logIn.buildSingleUseCase(param)
            .test()
            .assertValue(authUser)
    }

    @Test(expected = IllegalArgumentException::class)
    fun logInThrowsExceptionWhenParamIsMissing() {
        val authUser = generateAuthUser()
        val param = generateLogInParam()
        stubLogIn(param.email, param.password, Single.just(authUser))

        logIn.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun logInThrowsExceptionWhenParamIsNull() {
        val authUser = generateAuthUser()
        val param = generateLogInParam()
        stubLogIn(param.email, param.password, Single.just(authUser))

        logIn.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubLogIn(
        email: String,
        password: String,
        authSingle: Single<AuthUser>
    ) {
        every { repository.logIn(email, password) } returns authSingle
    }
}