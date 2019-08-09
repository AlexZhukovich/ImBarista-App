package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.testdata.domain.GenerateDomainTestData.generateUser
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class GetCurrentUserTest {

    private val repository = mockk<UserRepository>(relaxed = true)
    private val postExecutionThread = mockk<PostExecutionThread>()

    private val getCurrentUserInfo = GetCurrentUser(
        repository,
        postExecutionThread
    )

    @Test
    fun getCurrentUserInfoCompletesSuccessfully() {
        getCurrentUserInfo.buildSingleUseCase()
            .test()
            .assertComplete()
    }

    @Test
    fun getCurrentUserInfoReturnsCorrectData() {
        val user = generateUser()
        stubGetCurrentUserInfo(Single.just(user))

        getCurrentUserInfo.buildSingleUseCase()
            .test()
            .assertValue(user)
    }

    private fun stubGetCurrentUserInfo(userSingle: Single<User>) {
        every { repository.getCurrentUserInfo() } returns userSingle
    }
}