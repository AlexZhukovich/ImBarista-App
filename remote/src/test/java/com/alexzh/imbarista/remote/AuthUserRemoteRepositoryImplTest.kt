package com.alexzh.imbarista.remote

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import com.alexzh.imbarista.remote.mapper.SessionMapper
import com.alexzh.imbarista.remote.mapper.UserMapper
import com.alexzh.imbarista.remote.model.ResponseModel
import com.alexzh.imbarista.remote.model.SessionModel
import com.alexzh.imbarista.remote.model.UserModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import com.alexzh.testdata.base.RandomData.randomEmail
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString
import com.alexzh.testdata.data.GenerateDataTestData.generateSessionEntity
import com.alexzh.testdata.data.GenerateDataTestData.generateUserEntity
import com.alexzh.testdata.remote.GenerateRemoteTestData.generateSessionModel
import io.mockk.every
import io.mockk.mockk
import io.reactivex.Single
import org.junit.Test

class AuthUserRemoteRepositoryImplTest {

    private val service = mockk<CoffeeDrinksService>()
    private val userMapper = mockk<UserMapper>()
    private val sessionMapper = mockk<SessionMapper>()

    private val repository = AuthUserRemoteRepositoryImpl(service, userMapper, sessionMapper)

    @Test
    fun createAccountCompletesSuccessfully() {
        val userEntity = generateUserEntity()
        val user = UserModel(
            name = randomString(),
            email = randomEmail(),
            password = randomString()
        )

        val expectedResponse = ResponseModel(data = user)

        stubCreateUser(user, Single.just(expectedResponse))
        stubUserMapper(expectedResponse.data, userEntity)

        repository.createAccount(user.name, user.email, user.password)
            .test()
            .assertComplete()
    }

    @Test
    fun createAccountReturnsCorrectData() {
        val userEntity = generateUserEntity()
        val user = UserModel(
            name = randomString(),
            email = randomEmail(),
            password = randomString()
        )

        val expectedResponse = ResponseModel(data = user)

        stubCreateUser(user, Single.just(expectedResponse))
        stubUserMapper(expectedResponse.data, userEntity)

        repository.createAccount(user.name, user.email, user.password)
            .test()
            .assertValue(userEntity)
    }

    @Test
    fun logInCompletesSuccessfully() {
        val sessionEntity = generateSessionEntity()
        val sessionModel = generateSessionModel()
        val user = UserModel(
            email = randomEmail(),
            password = randomString()
        )

        val expectedResponse = ResponseModel(data = sessionModel)

        stubLogIn(user, Single.just(expectedResponse))
        stubSessionMapper(sessionModel, sessionEntity)

        repository.logIn(user.email, user.password)
            .test()
            .assertComplete()
    }

    @Test
    fun logInReturnsCorrectData() {
        val sessionEntity = generateSessionEntity()
        val sessionModel = generateSessionModel()
        val user = UserModel(
            email = randomEmail(),
            password = randomString()
        )

        val expectedResponse = ResponseModel(data = sessionModel)

        stubLogIn(user, Single.just(expectedResponse))
        stubSessionMapper(sessionModel, sessionEntity)

        repository.logIn(user.email, user.password)
            .test()
            .assertValue(sessionEntity)
    }

    @Test
    fun logOutCompletesSuccessfully() {
        val sessionId = randomLong()
        val accessToken = randomString()
        val sessionEntity = generateSessionEntity()
        val sessionModel = generateSessionModel()
        val expectedResponse = ResponseModel(data = sessionModel)

        stubLogOut(sessionId, accessToken, Single.just(expectedResponse))
        stubSessionMapper(sessionModel, sessionEntity)

        repository.logOut(sessionId, accessToken)
            .test()
            .assertComplete()
    }

    @Test
    fun logOutReturnsCorrectData() {
        val sessionId = randomLong()
        val accessToken = randomString()
        val sessionEntity = generateSessionEntity()
        val sessionModel = generateSessionModel()
        val expectedResponse = ResponseModel(data = sessionModel)

        stubLogOut(sessionId, accessToken, Single.just(expectedResponse))
        stubSessionMapper(sessionModel, sessionEntity)

        repository.logOut(sessionId, accessToken)
            .test()
            .assertValue(sessionEntity)
    }

    @Test
    fun refreshTokenThrowsException() {
        val accessToken = randomString()
        repository.refreshToken(accessToken)
            .test()
            .assertError(UnsupportedOperationException::class.java)
    }

    private fun stubCreateUser(user: UserModel, response: Single<ResponseModel<UserModel>>) {
        every { service.createUser(user) } returns response
    }

    private fun stubLogIn(user: UserModel, sessionSingle: Single<ResponseModel<SessionModel>>) {
        every { service.logIn(user) } returns sessionSingle
    }

    private fun stubLogOut(sessionId: Long, accessToken: String, sessionSingle: Single<ResponseModel<SessionModel>>) {
        every { service.logOut(sessionId, accessToken) } returns sessionSingle
    }

    private fun stubUserMapper(userModel: UserModel, userEntity: UserEntity) {
        every { userMapper.mapFromModel(userModel) } returns userEntity
    }

    private fun stubSessionMapper(sessionModel: SessionModel, sessionEntity: SessionEntity) {
        every { sessionMapper.mapFromModel(sessionModel) } returns sessionEntity
    }
}