package com.alexzh.imbarista.domain.interactor.login

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.imbarista.domain.repository.AuthRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException

class LogIn(
    private val authRepository: AuthRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<AuthUser, LogIn.Param>(postExecutionThread) {

    override fun buildSingleUseCase(params: Param?): Single<AuthUser> {
        if (params == null) throw IllegalArgumentException("Param can't be null")
        return authRepository.logIn(params.email, params.password)
    }

    data class Param(val email: String,
                     val password: String) {
        companion object {
            fun forLogIn(email: String,
                         password: String) : Param {
                return Param(email, password)
            }
        }
    }
}