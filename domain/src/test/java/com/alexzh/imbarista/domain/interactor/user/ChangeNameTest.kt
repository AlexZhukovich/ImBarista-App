package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.UserRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Test
import java.lang.IllegalArgumentException

class ChangeNameTest {

    private val repository = mockk<UserRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val changeName = ChangeName(
        repository,
        postExecutionThread
    )

    @Test
    fun changeNameCompletesSuccessfullyWhenParamIsCorrect() {
        val userId = 1L
        val newName = "new test name"
        val param = ChangeName.Param.forChangingName(userId, newName)
        stubChangeName(userId, newName, Completable.complete())

        changeName.buildCompletableUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun changeNameThrowsExceptionWhenParamIsMissing() {
        val userId = 1L
        val newName = "new test name"
        stubChangeName(userId, newName, Completable.complete())

        changeName.buildCompletableUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun changeNameThrowsExceptionWhenParamIsNull() {
        val userId = 1L
        val newName = "new test name"
        stubChangeName(userId, newName, Completable.complete())

        changeName.buildCompletableUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubChangeName(
        userId: Long,
        newName: String,
        completable: Completable
    ) {
        every { repository.changeName(userId, newName) } returns completable
    }
}