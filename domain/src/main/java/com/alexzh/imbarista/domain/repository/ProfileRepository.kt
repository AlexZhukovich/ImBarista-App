package com.alexzh.imbarista.domain.repository

import com.alexzh.imbarista.domain.model.User
import io.reactivex.Completable
import io.reactivex.Single

interface ProfileRepository {

    fun getUser(id: Long): Single<User>

    fun changeName(newName: String): Completable

    fun changePassword(newPassword: String): Completable
}