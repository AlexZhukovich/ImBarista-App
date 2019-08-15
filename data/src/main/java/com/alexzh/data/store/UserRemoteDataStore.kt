package com.alexzh.data.store

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import com.alexzh.data.repository.UserRemoteRepository
import io.reactivex.Single

class UserRemoteDataStore(
    private val repository: UserRemoteRepository
) : UserDataStore {

    override fun createAccount(name: String, email: String, password: String): Single<UserEntity> {
        return repository.createAccount(name, email, password)
    }

    override fun logIn(email: String, password: String): Single<SessionEntity> {
        return repository.logIn(email, password)
    }

    override fun logOut(sessionId: Long, accessToken: String): Single<SessionEntity> {
        return repository.logOut(sessionId, accessToken)
    }

    override fun refreshToken(sessionId: Long, accessToken: String, refreshToken: String): Single<SessionEntity> {
        return repository.refreshToken(sessionId, accessToken, refreshToken)
    }

    override fun getCurrentUser(accessToken: String): Single<UserEntity> {
        return repository.getCurrentUser(accessToken)
    }
}