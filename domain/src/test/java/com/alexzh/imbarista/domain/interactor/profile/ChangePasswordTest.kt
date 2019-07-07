package com.alexzh.imbarista.domain.interactor.profile

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.ProfileRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Test
import java.lang.IllegalArgumentException

class ChangePasswordTest {

    private val repository = mockk<ProfileRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val changePassword = ChangePassword(
        repository,
        postExecutionThread
    )

    @Test
    fun changePasswordCompletesSuccessfullyWhenParamIsCorrect() {
        val newPassword = "new test password"
        val param = ChangePassword.Param.forChangingPassword(newPassword)
        stubChangePassword(newPassword, Completable.complete())

        changePassword.buildCompletableUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun changePasswordThrowsExceptionWhenParamIsMissing() {
        val newPassword = "new test password"
        stubChangePassword(newPassword, Completable.complete())

        changePassword.buildCompletableUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun changePasswordThrowsExceptionWhenParamIsNull() {
        val newPassword = "new test password"
        stubChangePassword(newPassword, Completable.complete())

        changePassword.buildCompletableUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubChangePassword(newPassword: String, completable: Completable) {
        every { repository.changePassword(newPassword) } returns completable
    }
}