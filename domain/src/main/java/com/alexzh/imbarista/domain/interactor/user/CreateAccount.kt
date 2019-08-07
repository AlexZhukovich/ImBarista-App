package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Single

class CreateAccount(
    private val userRepository: UserRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<User, CreateAccount.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<User> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return userRepository.createAccount(
            param.name,
            param.email,
            param.password
        )
    }

    data class Param(
        val name: String,
        val email: String,
        val password: String
    ) {
        companion object {

            fun forCreatingAccount(
                name: String,
                email: String,
                password: String
            ): Param {
                return Param(name, email, password)
            }
        }
    }
}