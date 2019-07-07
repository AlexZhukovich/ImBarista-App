package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.AuthUser
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException

class LogIn(
    private val userRepository: UserRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<AuthUser, LogIn.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<AuthUser> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return userRepository.logIn(
            param.email,
            param.password
        )
    }

    data class Param(
        val email: String,
        val password: String
    ) {
        companion object {

            fun forLogIn(
                email: String,
                password: String
            ): Param {
                return Param(email, password)
            }
        }
    }
}