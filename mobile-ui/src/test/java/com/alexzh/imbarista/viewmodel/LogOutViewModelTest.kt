package com.alexzh.imbarista.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexzh.imbarista.domain.interactor.user.LogOut
import com.alexzh.imbarista.state.ResourceState
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.observers.DisposableCompletableObserver
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class LogOutViewModelTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val logOut = mockk<LogOut>(relaxed = true)

    private val viewModel = LogOutViewModel(logOut)

    private val slot = slot<DisposableCompletableObserver>()

    @Test
    fun logOutExecutesUseCase() {
        viewModel.logOut()

        verify { logOut.execute(any()) }
    }

    @Test
    fun logOutReturnsSuccess() {
        viewModel.logOut()
        verify { logOut.execute(capture(slot)) }
        slot.captured.onComplete()

        assertEquals(ResourceState.SUCCESS, viewModel.getLogOutInfo().value?.status)
    }

    @Test
    fun logOutReturnsError() {
        viewModel.logOut()
        verify { logOut.execute(capture(slot)) }
        slot.captured.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, viewModel.getLogOutInfo().value?.status)
    }
}