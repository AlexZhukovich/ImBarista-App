package com.alexzh.imbarista.domain.repository

import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun createAccount(name: String, email: String, password: String): Single<User>

    fun logIn(email: String, password: String): Single<Session>

    fun logOut(): Completable

    fun getUser(userId: Long): Single<User>

    fun getExistingSession(): Single<Session>
}