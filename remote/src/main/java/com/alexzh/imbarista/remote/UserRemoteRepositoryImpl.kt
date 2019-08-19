package com.alexzh.imbarista.remote

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import com.alexzh.data.repository.UserRemoteRepository
import com.alexzh.imbarista.remote.mapper.SessionMapper
import com.alexzh.imbarista.remote.mapper.UserMapper
import com.alexzh.imbarista.remote.model.RefreshTokenModel
import com.alexzh.imbarista.remote.model.UserModel
import com.alexzh.imbarista.remote.service.CoffeeDrinksService
import io.reactivex.Single

class UserRemoteRepositoryImpl(
    private val service: CoffeeDrinksService,
    private val userMapper: UserMapper,
    private val sessionMapper: SessionMapper
) : UserRemoteRepository {

    override fun createAccount(name: String, email: String, password: String): Single<UserEntity> {
        val user = UserModel(
            name = name,
            email = email,
            password = password
        )
        return service.createUser(user)
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
            .map { sessionMapper.mapFromModel(it.data) }
    }

    override fun refreshToken(sessionId: Long, accessToken: String, refreshToken: String): Single<SessionEntity> {
        return service.refreshToken(sessionId, accessToken, RefreshTokenModel(refreshToken))
            .map { sessionMapper.mapFromModel(it.data) }
    }

    override fun getCurrentUser(accessToken: String): Single<UserEntity> {
        return service.getCurrentUser(accessToken)
            .map { userMapper.mapFromModel(it.data) }
    }
}