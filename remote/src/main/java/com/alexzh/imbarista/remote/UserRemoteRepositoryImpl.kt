package com.alexzh.imbarista.remote

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import com.alexzh.data.repository.UserRemoteRepository
import com.alexzh.imbarista.remote.mapper.SessionRemoteMapper
import com.alexzh.imbarista.remote.mapper.UserAlreadyExistExceptionRemoteMapper
import com.alexzh.imbarista.remote.mapper.UserRemoteMapper
import com.alexzh.imbarista.remote.model.RefreshTokenModel
import com.alexzh.imbarista.remote.model.ResponseModel
import com.alexzh.imbarista.remote.model.UserModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.reactivex.Single
import retrofit2.HttpException

class UserRemoteRepositoryImpl(
    private val service: CoffeeDrinksService,
    private val userRemoteMapper: UserRemoteMapper,
    private val sessionRemoteMapper: SessionRemoteMapper,
    private val userAlreadyExceptionExceptionRemoteMapper: UserAlreadyExistExceptionRemoteMapper
) : UserRemoteRepository {

    override fun createAccount(name: String, email: String, password: String): Single<UserEntity> {
        val user = UserModel(
            name = name,
            email = email,
            password = password
        )
        return service.createUser(user)
            .onErrorResumeNext { handleCreateAccountError(it) }
            .map { userRemoteMapper.mapFromModel(it.data) }
    }

    private fun handleCreateAccountError(error: Throwable): Single<ResponseModel<UserModel>> {
        if (error is HttpException && error.code() == 409) {
            return Single.error(userAlreadyExceptionExceptionRemoteMapper.mapToDataException(error))
        }
        return Single.error(error)
    }

    override fun logIn(email: String, password: String): Single<SessionEntity> {
        val user = UserModel(
            email = email,
            password = password
        )
        return service.logIn(user)
            .map { sessionRemoteMapper.mapFromModel(it.data) }
    }

    override fun logOut(sessionId: Long, accessToken: String): Single<SessionEntity> {
        return service.logOut(sessionId, accessToken)
            .map { sessionRemoteMapper.mapFromModel(it.data) }
    }

    override fun refreshToken(sessionId: Long, accessToken: String, refreshToken: String): Single<SessionEntity> {
        return service.refreshToken(sessionId, accessToken, RefreshTokenModel(refreshToken))
            .map { sessionRemoteMapper.mapFromModel(it.data) }
    }

    override fun getCurrentUser(accessToken: String): Single<UserEntity> {
        return service.getCurrentUser(accessToken)
            .map { userRemoteMapper.mapFromModel(it.data) }
    }
}