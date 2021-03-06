package com.alexzh.data

import com.alexzh.data.mapper.SessionDataMapper
import com.alexzh.data.mapper.UserDataMapper
import com.alexzh.data.model.HttpDataException
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.data.store.UserDataStore
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Completable
import io.reactivex.Single

class UserDataRepository(
    private val userDataStore: UserDataStore,
    private val userDataMapper: UserDataMapper,
    private val sessionDataMapper: SessionDataMapper,
    private val preferencesRepository: PreferencesRepository
) : UserRepository {

    companion object {
        const val REPEAT_REQUEST_COUNT = 3L
    }

    override fun createAccount(name: String, email: String, password: String): Single<User> {
        return userDataStore.createAccount(name, email, password)
            .retry(REPEAT_REQUEST_COUNT)
            .map { userDataMapper.mapFromEntity(it) }
    }

    override fun logIn(email: String, password: String): Single<Session> {
        return userDataStore.logIn(email, password)
            .retry(REPEAT_REQUEST_COUNT)
            .flatMap {
                preferencesRepository.saveSessionInfo(it)
                Single.just(it)
            }
            .map { sessionDataMapper.mapFromEntity(it) }
    }

    override fun logOut(): Completable {
        return Completable.defer {
            userDataStore.logOut().blockingGet()
            preferencesRepository.clearSessionInfo()
            Completable.complete()
        }
    }

    override fun refreshToken(): Single<Session> {
        return userDataStore.refreshToken()
            .retry(REPEAT_REQUEST_COUNT)
            .flatMap {
                preferencesRepository.saveSessionInfo(it)
                Single.just(it)
            }
            .map { sessionDataMapper.mapFromEntity(it) }
    }

    override fun getCurrentUserInfo(): Single<User> {
        return userDataStore.getCurrentUser()
            .onErrorResumeNext { error ->
                if (error is HttpDataException && error.code == 401) {
                    refreshToken().blockingGet()
                    return@onErrorResumeNext userDataStore.getCurrentUser()
                }
                return@onErrorResumeNext Single.error(error)
            }
            .retry(REPEAT_REQUEST_COUNT)
            .map { userDataMapper.mapFromEntity(it) }
    }

    override fun getExistingSession(): Single<Session> {
        val session = preferencesRepository.getSessionInfo()

        return if (session.sessionId != -1L) {
            Single.just(preferencesRepository.getSessionInfo())
                .map { sessionDataMapper.mapFromEntity(it) }
        } else {
            Single.error(IllegalArgumentException("No data stored in shared preferences"))
        }


    }
}