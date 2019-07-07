package com.alexzh.imbarista.domain.repository

import io.reactivex.Completable

interface ProfileRepository {

    fun changeName(newName: String): Completable

    fun changePassword(newPassword: String): Completable
}