package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.testdata.domain.GenerateDomainTestData.generateGetUserByIdParam
import com.alexzh.testdata.domain.GenerateDomainTestData.generateUser
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test
import java.lang.IllegalArgumentException

class GetUserByIdTest {

    private val repository = mockk<UserRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getUserById = GetUserById(
        repository,
        postExecutionThread
    )

    @Test
    fun getUserByIdCompletesSuccessfullyWhenParamIsCorrect() {
        val user = generateUser()
        val param = generateGetUserByIdParam(user.id)
        stubGetUserById(param.userId, Single.just(user))

        getUserById.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun getUserByIdReturnsCorrectDataWhenParamIsCorrect() {
        val user = generateUser()
        val param = generateGetUserByIdParam(user.id)
        stubGetUserById(param.userId, Single.just(user))

        getUserById.buildSingleUseCase(param)
            .test()
            .assertValue(user)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUserByIdThrowsExceptionWhenParamIsMissing() {
        val user = generateUser()
        val param = generateGetUserByIdParam(user.id)
        stubGetUserById(param.userId, Single.just(user))

        getUserById.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUserByIdThrowsExceptionWhenParamIsNull() {
        val user = generateUser()
        val param = generateGetUserByIdParam(user.id)
        stubGetUserById(param.userId, Single.just(user))

        getUserById.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubGetUserById(userId: Long, userSingle: Single<User>) {
        every { repository.getUser(userId) } returns userSingle
    }
}