package com.alexzh.data.repository

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import io.reactivex.Single

interface AuthUserRemoteRepository {

    fun createAccount(name: String, email: String, password: String): Single<UserEntity>

    fun logIn(email: String, password: String): Single<SessionEntity>

    fun logOut(sessionId: Long, accessToken: String): Single<SessionEntity>

    fun refreshToken(accessToken: String): Single<SessionEntity>
}