package com.alexzh.data.store

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity
import io.reactivex.Single

interface UserDataStore {

    fun createAccount(name: String, email: String, password: String): Single<UserEntity>

    fun logIn(email: String, password: String): Single<SessionEntity>

    fun logOut(): Single<SessionEntity>

    fun refreshToken(): Single<SessionEntity>

    fun getCurrentUser(): Single<UserEntity>
}