package com.alexzh.data

import com.alexzh.data.mapper.SessionMapper
import com.alexzh.data.mapper.UserMapper
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.data.store.UserDataStore
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserDataRepository(
    private val userDataStore: UserDataStore,
    private val userMapper: UserMapper,
    private val sessionMapper: SessionMapper,
    private val preferencesRepository: PreferencesRepository
) : UserRepository {

    override fun createAccount(name: String, email: String, password: String): Single<User> {
        return userDataStore.createAccount(name, email, password)
            .map { userMapper.mapFromEntity(it) }
    }

    override fun logIn(email: String, password: String): Single<Session> {
        return userDataStore.logIn(email, password)
            .flatMap {
                preferencesRepository.saveSessionInfo(it)
                Single.just(it)
            }
            .map { sessionMapper.mapFromEntity(it) }
    }

    override fun logOut(): Completable {
        return Completable.defer {
            val sessionEntity = preferencesRepository.getSessionInfo()
            userDataStore.logOut(sessionEntity.sessionId, sessionEntity.accessToken).blockingGet()
            preferencesRepository.clearSessionInfo()
            Completable.complete()
        }
    }

    override fun getUser(userId: Long): Single<User> {
        return Single.error(UnsupportedOperationException("The getUser operation is not supported"))
    }

    override fun getExistingSession(): Single<Session> {
        return Single.just(preferencesRepository.getSessionInfo())
            .map { sessionMapper.mapFromEntity(it) }
    }
}