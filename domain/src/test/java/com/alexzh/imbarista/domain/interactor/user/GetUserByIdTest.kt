package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
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
        val userId = 1L
        val user = User(
            userId,
            "Test",
            "test@test.com",
            "test-password"
        )
        val param = GetUserById.Param.forUser(userId)
        stubGetUserById(userId, Single.just(user))

        getUserById.buildSingleUseCase(param)
            .test()
            .assertComplete()
    }

    @Test
    fun getUserByIdReturnsCorrectDataWhenParamIsCorrect() {
        val userId = 1L
        val user = User(
            userId,
            "Test",
            "test@test.com",
            "test-password"
        )
        val param = GetUserById.Param.forUser(userId)
        stubGetUserById(userId, Single.just(user))

        getUserById.buildSingleUseCase(param)
            .test()
            .assertValue(user)
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUserByIdThrowsExceptionWhenParamIsMissing() {
        val userId = 1L
        val user = User(
            userId,
            "Test",
            "test@test.com",
            "test-password"
        )
        stubGetUserById(userId, Single.just(user))

        getUserById.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun getUserByIdThrowsExceptionWhenParamIsNull() {
        val userId = 1L
        val user = User(
            userId,
            "Test",
            "test@test.com",
            "test-password"
        )
        stubGetUserById(userId, Single.just(user))

        getUserById.buildSingleUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubGetUserById(userId: Long, userSingle: Single<User>) {
        every { repository.getUser(userId) } returns userSingle
    }
}