package com.alexzh.imbarista.remote

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import com.alexzh.data.repository.UserRemoteRepository
import com.alexzh.imbarista.remote.mapper.AuthExceptionMapper
import com.alexzh.imbarista.remote.mapper.SessionMapper
import com.alexzh.imbarista.remote.mapper.UserAlreadyExistExceptionMapper
import com.alexzh.imbarista.remote.mapper.UserMapper
import com.alexzh.imbarista.remote.model.RefreshTokenModel
import com.alexzh.imbarista.remote.model.ResponseModel
import com.alexzh.imbarista.remote.model.UserModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.reactivex.Single
import retrofit2.HttpException

class UserRemoteRepositoryImpl(
    private val service: CoffeeDrinksService,
    private val userMapper: UserMapper,
    private val sessionMapper: SessionMapper,
    private val userAlreadyExceptionExceptionMapper: UserAlreadyExistExceptionMapper,
    private val authExceptionMapper: AuthExceptionMapper
) : UserRemoteRepository {

    override fun createAccount(name: String, email: String, password: String): Single<UserEntity> {
        val user = UserModel(
            name = name,
            email = email,
            password = password
        )
        return service.createUser(user)
            .onErrorResumeNext { handleCreateAccountError(it) }
            .map { userMapper.mapFromModel(it.data) }
    }

    override fun logIn(email: String, password: String): Single<SessionEntity> {
        val user = UserModel(
            email = email,
            password = password
        )
        return service.logIn(user)
            .map { sessionMapper.mapFromModel(it.data) }
    }

    override fun logOut(sessionId: Long, accessToken: String): Single<SessionEntity> {
        return service.logOut(sessionId, accessToken)
            .onErrorResumeNext { handleAuthError(it) }
            .map { sessionMapper.mapFromModel(it.data) }
    }

    override fun refreshToken(sessionId: Long, accessToken: String, refreshToken: String): Single<SessionEntity> {
        return service.refreshToken(sessionId, accessToken, RefreshTokenModel(refreshToken))
            .onErrorResumeNext { handleAuthError(it) }
            .map { sessionMapper.mapFromModel(it.data) }
    }

    override fun getCurrentUser(accessToken: String): Single<UserEntity> {
        return service.getCurrentUser(accessToken)
            .onErrorResumeNext { handleAuthError(it) }
            .map { userMapper.mapFromModel(it.data) }
    }

    private fun <T> handleAuthError(error: Throwable): Single<T> {
        if (error is HttpException && error.code() == 401) {
            return Single.error(authExceptionMapper.mapToDataException(error))
        }
        return Single.error(error)
    }

    private fun handleCreateAccountError(error: Throwable): Single<ResponseModel<UserModel>> {
        if (error is HttpException && error.code() == 409) {
            return Single.error(userAlreadyExceptionExceptionMapper.mapToDataException(error))
        }
        return Single.error(error)
    }
}