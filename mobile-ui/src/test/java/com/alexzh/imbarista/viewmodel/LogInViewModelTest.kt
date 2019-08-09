package com.alexzh.imbarista.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexzh.imbarista.R
import com.alexzh.imbarista.domain.interactor.user.LogIn
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
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class LogInViewModelTest {

    companion object {
        const val NO_ERROR_CODE = -1
    }

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val logIn = mockk<LogIn>(relaxed = true)

    private val sessionMapper = mockk<SessionViewMapper>()

    private val sessionSlot = slot<DisposableSingleObserver<Session>>()

    private val viewModel = LogInViewModel(
        logIn,
        sessionMapper
    )

    @Test
    fun logInExecutesUseCase() {
        viewModel.logIn("test@test.com", "password")

        verify { logIn.execute(any(), any()) }
    }

    @Test
    fun logInReturnsSuccess() {
        val session = generateSession()
        val sessionView = generateSessionView()
        stubSessionMapperMapsToView(session, sessionView)

        viewModel.logIn("test@test.com", "password")
        verify { logIn.execute(capture(sessionSlot), any()) }
        sessionSlot.captured.onSuccess(session)

        assertEquals(ResourceState.SUCCESS, viewModel.getLogInInfo().value?.status)
    }

    @Test
    fun logInReturnsCorrectData() {
        val session = generateSession()
        val sessionView = generateSessionView()
        stubSessionMapperMapsToView(session, sessionView)

        viewModel.logIn("test@test.com", "password")
        verify { logIn.execute(capture(sessionSlot), any()) }
        sessionSlot.captured.onSuccess(session)

        assertEquals(sessionView, viewModel.getLogInInfo().value?.data)
    }

    @Test
    fun logInReturnsError() {
        val session = generateSession()
        val sessionView = generateSessionView()
        stubSessionMapperMapsToView(session, sessionView)

        viewModel.logIn("test@test.com", "password")
        verify { logIn.execute(capture(sessionSlot), any()) }
        sessionSlot.captured.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, viewModel.getLogInInfo().value?.status)
    }

    @Test
    fun changeEmailTextReturnsEmailIsBlankErrorWhenEmailIsBlank() {
        viewModel.changeEmailText("")

        assertEquals(R.string.error_email_is_blank, viewModel.getEmailError().value)
    }

    @Test
    fun changeEmailTextReturnsTextIsNotEmailErrorWhenEmailIsIncorrect() {
        viewModel.changeEmailText("test")

        assertEquals(R.string.error_email_is_not_email, viewModel.getEmailError().value)
    }

    @Test
    fun changeEmailTextReturnsMinusOneErrorWhenEmailIsCorrect() {
        viewModel.changeEmailText("test@test.com")

        assertEquals(NO_ERROR_CODE, viewModel.getEmailError().value)
    }

    @Test
    fun changePasswordTextReturnsPasswordIsBlankErrorWhenPasswordIsBlank() {
        viewModel.changePasswordText("")

        assertEquals(R.string.error_password_is_blank, viewModel.getPasswordError().value)
    }

    @Test
    fun changePasswordTextReturnsMinusOneErrorWhenPasswordIsCorrect() {
        viewModel.changePasswordText("test")

        assertEquals(NO_ERROR_CODE, viewModel.getPasswordError().value)
    }

    private fun stubSessionMapperMapsToView(session: Session, sessionView: SessionView) {
        every { sessionMapper.mapToView(session) } returns sessionView
    }
}