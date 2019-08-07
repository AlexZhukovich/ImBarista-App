package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.testdata.domain.GenerateDomainTestData.generateLogInParam
import com.alexzh.testdata.domain.GenerateDomainTestData.generateSession
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class LogInTest {

    private val repository = mockk<UserRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val logIn = LogIn(
        repository,
        postExecutionThread
    )

    @Test
    fun logInCompletesSuccessfullyWhenParamsAreCorrect() {
        val session = generateSession()
        val param = generateLogInParam()
        stubLogIn(param.email, param.password, Single.just(session))

        logIn.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun logInReturnsCorrectDataWhenParamsAreCorrect() {
        val session = generateSession()
        val param = generateLogInParam()
        stubLogIn(param.email, param.password, Single.just(session))

        logIn.buildSingleUseCase(param)
            .test()
            .assertValue(session)
    }

    @Test(expected = IllegalArgumentException::class)
    fun logInThrowsExceptionWhenParamIsMissing() {
        val session = generateSession()
        val param = generateLogInParam()
        stubLogIn(param.email, param.password, Single.just(session))

        logIn.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun logInThrowsExceptionWhenParamIsNull() {
        val session = generateSession()
        val param = generateLogInParam()
        stubLogIn(param.email, param.password, Single.just(session))

        logIn.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubLogIn(
        email: String,
        password: String,
        sessionSingle: Single<Session>
    ) {
        every { repository.logIn(email, password) } returns sessionSingle
    }
}