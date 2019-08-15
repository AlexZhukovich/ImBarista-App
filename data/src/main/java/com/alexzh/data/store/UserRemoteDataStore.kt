package com.alexzh.data.store

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.data.repository.UserRemoteRepository
import io.reactivex.Single

class UserRemoteDataStore(
    private val repository: UserRemoteRepository,
    private val preferencesRepository: PreferencesRepository
) : UserDataStore {

    override fun createAccount(name: String, email: String, password: String): Single<UserEntity> {
        return repository.createAccount(name, email, password)
    }

    override fun logIn(email: String, password: String): Single<SessionEntity> {
        return repository.logIn(email, password)
    }

    override fun logOut(): Single<SessionEntity> {
        val sessionEntity = preferencesRepository.getSessionInfo()
        return repository.logOut(sessionEntity.sessionId, sessionEntity.accessToken)
    }

    override fun refreshToken(): Single<SessionEntity> {
        val sessionEntity = preferencesRepository.getSessionInfo()
        return repository.refreshToken(sessionEntity.sessionId, sessionEntity.accessToken, sessionEntity.refreshToken)
    }

    override fun getCurrentUser(): Single<UserEntity> {
        val sessionEntity = preferencesRepository.getSessionInfo()
        return repository.getCurrentUser(sessionEntity.accessToken)
    }
}