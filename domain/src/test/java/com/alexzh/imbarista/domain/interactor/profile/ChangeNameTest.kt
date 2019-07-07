package com.alexzh.imbarista.domain.interactor.profile

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.repository.ProfileRepository
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Completable
import org.junit.Test
import java.lang.IllegalArgumentException

class ChangeNameTest {

    private val repository = mockk<ProfileRepository>()
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val changeName = ChangeName(
        repository,
        postExecutionThread
    )

    @Test
    fun changeNameCompletesSuccessfullyWhenParamIsCorrect() {
        val newName = "new test name"
        val param = ChangeName.Param.forChangingName(newName)
        stubChangeName(newName, Completable.complete())

        changeName.buildCompletableUseCase(param)
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun changeNameThrowsExceptionWhenParamIsMissing() {
        val newName = "new test name"
        stubChangeName(newName, Completable.complete())

        changeName.buildCompletableUseCase()
            .test()
            .assertComplete()
    }

    @Test(expected = IllegalArgumentException::class)
    fun changeNameThrowsExceptionWhenParamIsNull() {
        val newName = "new test name"
        stubChangeName(newName, Completable.complete())

        changeName.buildCompletableUseCase(null)
            .test()
            .assertComplete()
    }

    private fun stubChangeName(newName: String, completable: Completable) {
        every { repository.changeName(newName) } returns completable
    }
}