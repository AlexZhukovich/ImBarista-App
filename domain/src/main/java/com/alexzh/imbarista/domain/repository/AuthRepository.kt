package com.alexzh.imbarista.domain.repository

import com.alexzh.imbarista.domain.model.AuthUser
import io.reactivex.Single

interface AuthRepository {

    fun logIn(email: String,
              password: String): Single<AuthUser>

    fun createAccount(name: String,
                      email: String,
                      password: String): Single<AuthUser>
}