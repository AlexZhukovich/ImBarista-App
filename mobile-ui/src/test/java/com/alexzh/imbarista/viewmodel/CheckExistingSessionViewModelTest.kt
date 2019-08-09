package com.alexzh.imbarista.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexzh.imbarista.domain.interactor.user.GetExistingSession
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.mapper.SessionViewMapper
import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.testdata.GenerateMobileUiTestData.generateSessionView
import com.alexzh.testdata.domain.GenerateDomainTestData.generateSession
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.lang.RuntimeException

class CheckExistingSessionViewModelTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getExistingSession = mockk<GetExistingSession>(relaxed = true)

    private val sessionMapper = mockk<SessionViewMapper>(relaxed = true)

    private val slot = slot<DisposableSingleObserver<Session>>()

    private val viewModel = CheckExistingSessionViewModel(
        getExistingSession,
        sessionMapper
    )

    @Test
    fun checkExistingSessionExecutesUseCase() {
        viewModel.checkExistingSession()

        verify { getExistingSession.execute(any()) }
    }

    @Test
    fun checkExistingSessionReturnSuccess() {
        val session = generateSession()
        val sessionView = generateSessionView()
        stubSessionMapperMapsToView(session, sessionView)

        viewModel.checkExistingSession()
        verify { getExistingSession.execute(capture(slot)) }
        slot.captured.onSuccess(session)

        assertEquals(ResourceState.SUCCESS, viewModel.getExistingSessionInfo().value?.status)
    }

    @Test
    fun checkExistingSessionReturnError() {
        viewModel.checkExistingSession()
        verify { getExistingSession.execute(capture(slot)) }
        slot.captured.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, viewModel.getExistingSessionInfo().value?.status)
    }

    @Test
    fun checkExistingSessionReturnCorrectData() {
        val session = generateSession()
        val sessionView = generateSessionView()
        stubSessionMapperMapsToView(session, sessionView)

        viewModel.checkExistingSession()
        verify { getExistingSession.execute(capture(slot)) }
        slot.captured.onSuccess(session)

        assertEquals(sessionView, viewModel.getExistingSessionInfo().value?.data)
    }

    private fun stubSessionMapperMapsToView(session: Session, sessionView: SessionView) {
        every { sessionMapper.mapToView(session) } returns sessionView
    }
}