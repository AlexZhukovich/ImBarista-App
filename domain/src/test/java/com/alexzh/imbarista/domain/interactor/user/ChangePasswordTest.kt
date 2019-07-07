package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Test
import java.lang.IllegalArgumentException

class ChangePasswordTest {

    private val repository = mockk<UserRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val changePassword = ChangePassword(
        repository,
        postExecutionThread
    )

    @Test
    fun changePasswordCompletesSuccessfullyWhenParamIsCorrect() {
        val userId = 1L
        val newPassword = "new test password"
        val param = ChangePassword.Param.forChangingPassword(userId, newPassword)
        stubChangePassword(userId, newPassword, Completable.complete())

        changePassword.buildCompletableUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun changePasswordThrowsExceptionWhenParamIsMissing() {
        val userId = 1L
        val newPassword = "new test password"
        stubChangePassword(userId, newPassword, Completable.complete())

        changePassword.buildCompletableUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun changePasswordThrowsExceptionWhenParamIsNull() {
        val userId = 1L
        val newPassword = "new test password"
        stubChangePassword(userId, newPassword, Completable.complete())

        changePassword.buildCompletableUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubChangePassword(
        userId: Long,
        newPassword: String,
        completable: Completable
    ) {
        every { repository.changePassword(userId, newPassword) } returns completable
    }
}