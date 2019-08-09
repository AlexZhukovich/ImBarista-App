package com.alexzh.data.store

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import io.reactivex.Single

interface UserDataStore {

    fun createAccount(name: String, email: String, password: String): Single<UserEntity>

    fun logIn(email: String, password: String): Single<SessionEntity>

    fun logOut(sessionId: Long, accessToken: String): Single<SessionEntity>

    fun refreshToken(accessToken: String): Single<SessionEntity>

    fun getCurrentUser(accessToken: String): Single<UserEntity>
}