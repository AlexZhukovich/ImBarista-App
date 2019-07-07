package com.alexzh.imbarista.domain.repository

import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.imbarista.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface UserRepository {

    fun logIn(email: String, password: String): Single<AuthUser>

    fun createAccount(name: String, email: String, password: String): Single<AuthUser>

    fun logOut(): Completable

    fun getUser(userId: Long): Single<User>

    fun changeName(userId: Long, newName: String): Completable

    fun changePassword(userId: Long, newPassword: String): Completable
}