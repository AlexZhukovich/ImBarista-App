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

    override fun refreshToken(accessToken: String): Single<SessionEntity> {
        return repository.refreshToken(accessToken)
    }

    override fun getUser(userId: Long): Single<UserEntity> {
        return Single.error(UnsupportedOperationException("Not implemented yet"))
    }
}