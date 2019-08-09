package com.alexzh.imbarista.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alexzh.imbarista.domain.interactor.user.GetCurrentUser
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.mapper.UserViewMapper
import com.alexzh.imbarista.model.UserView
import com.alexzh.imbarista.state.ResourceState
import com.alexzh.imbarista.testdata.GenerateMobileUiTestData.generateUserView
import com.alexzh.testdata.domain.GenerateDomainTestData.generateUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.reactivex.observers.DisposableSingleObserver
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class GetCurrentUserViewModelTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val getCurrentUserInfo = mockk<GetCurrentUser>(relaxed = true)

    private val userMapper = mockk<UserViewMapper>()

    private val userSlot = slot<DisposableSingleObserver<User>>()

    private val viewModel = GetCurrentUserViewModel(
        getCurrentUserInfo,
        userMapper
    )

    @Test
    fun fetchUserInfoExecutesUseCase() {
        viewModel.fetchUserInfo()

        verify { getCurrentUserInfo.execute(any()) }
    }

    @Test
    fun fetchUserInfoReturnsSuccess() {
        val user = generateUser()
        val userView = generateUserView()
        stubUserMapperMapsToView(user, userView)

        viewModel.fetchUserInfo()
        verify { getCurrentUserInfo.execute(capture(userSlot)) }
        userSlot.captured.onSuccess(user)

        Assert.assertEquals(ResourceState.SUCCESS, viewModel.getUserInfo().value?.status)
    }

    @Test
    fun fetchUserInfoReturnsCorrectData() {
        val user = generateUser()
        val userView = generateUserView()
        stubUserMapperMapsToView(user, userView)

        viewModel.fetchUserInfo()
        verify { getCurrentUserInfo.execute(capture(userSlot)) }
        userSlot.captured.onSuccess(user)

        Assert.assertEquals(userView, viewModel.getUserInfo().value?.data)
    }

    @Test
    fun fetchUserInfoReturnsError() {
        val user = generateUser()
        val userView = generateUserView()
        stubUserMapperMapsToView(user, userView)

        viewModel.fetchUserInfo()
        verify { getCurrentUserInfo.execute(capture(userSlot)) }
        userSlot.captured.onError(RuntimeException())

        Assert.assertEquals(ResourceState.ERROR, viewModel.getUserInfo().value?.status)
    }

    private fun stubUserMapperMapsToView(user: User, userView: UserView) {
        every { userMapper.mapToView(user) } returns userView
    }
}