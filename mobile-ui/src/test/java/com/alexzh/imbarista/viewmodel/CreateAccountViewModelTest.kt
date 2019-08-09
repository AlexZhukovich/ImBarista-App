package com.alexzh.imbarista.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexzh.imbarista.R
import com.alexzh.imbarista.domain.interactor.user.CreateAccount
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.mapper.UserViewMapper
import com.alexzh.imbarista.model.UserView
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.testdata.GenerateMobileUiTestData.generateUserView
import com.alexzh.testdata.base.RandomData.randomEmail
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.domain.GenerateDomainTestData.generateUser
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
class CreateAccountViewModelTest {

    companion object {
        const val NO_ERROR_CODE = -1
    }

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val createAccount = mockk<CreateAccount>(relaxed = true)

    private val userMapper = mockk<UserViewMapper>(relaxed = true)

    private val userSlot = slot<DisposableSingleObserver<User>>()

    private val viewModel = CreateAccountViewModel(
        createAccount,
        userMapper
    )

    @Test
    fun createAccountExecuteUseCase() {
        viewModel.createAccount(
            name = randomString(),
            email = randomEmail(),
            password = randomString()
        )

        verify { createAccount.execute(any(), any()) }
    }

    @Test
    fun createAccountExecuteReturnsSuccess() {
        val user = generateUser()

        viewModel.createAccount(user.name, user.email, "password")
        verify { createAccount.execute(capture(userSlot), any()) }
        userSlot.captured.onSuccess(user)

        assertEquals(ResourceState.SUCCESS, viewModel.getUserInfo().value?.status)
    }

    @Test
    fun createAccountExecuteReturnsCorrectData() {
        val user = generateUser()
        val userView = generateUserView()
        stubUserMapperMapsToView(user, userView)

        viewModel.createAccount(user.name, user.email, "password")
        verify { createAccount.execute(capture(userSlot), any()) }
        userSlot.captured.onSuccess(user)

        assertEquals(userView, viewModel.getUserInfo().value?.data)
    }

    @Test
    fun createAccountExecuteReturnsError() {
        val user = generateUser()

        viewModel.createAccount(user.name, user.email, "password")
        verify { createAccount.execute(capture(userSlot), any()) }
        userSlot.captured.onError(RuntimeException())

        assertEquals(ResourceState.ERROR, viewModel.getUserInfo().value?.status)
    }

    @Test
    fun changeNameTextReturnsNameIsBlankErrorWhenNameIsBlank() {
        viewModel.changeNameText("")

        assertEquals(R.string.error_name_is_blank, viewModel.getNameError().value)
    }

    @Test
    fun changeNameTextReturnsMinusOneErrorWhenNameIsCorrect() {
        viewModel.changeNameText("test")

        assertEquals(NO_ERROR_CODE, viewModel.getNameError().value)
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
    fun changeEmailTextReturnsMinusOneErrorWhenEmailIsIncorrect() {
        viewModel.changeEmailText("test@test.com")

        assertEquals(NO_ERROR_CODE, viewModel.getEmailError().value)
    }

    @Test
    fun changePasswordTextReturnsNameIsBlankErrorWhenPasswordIsBlank() {
        viewModel.changePasswordText("")

        assertEquals(R.string.error_password_is_blank, viewModel.getPasswordError().value)
    }

    @Test
    fun changePasswordTextReturnsMinusOneErrorWhenPasswordIsCorrect() {
        viewModel.changePasswordText("test")

        assertEquals(NO_ERROR_CODE, viewModel.getPasswordError().value)
    }

    private fun stubUserMapperMapsToView(user: User, userView: UserView) {
        every { userMapper.mapToView(user) } returns userView
    }
}