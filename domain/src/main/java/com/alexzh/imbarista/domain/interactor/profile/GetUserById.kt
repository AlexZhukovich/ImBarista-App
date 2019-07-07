package com.alexzh.imbarista.domain.interactor.profile

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException

class GetUserById(
    private val userRepository: UserRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<User, GetUserById.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<User> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return userRepository.getUser(param.userId)
    }

    data class Param(val userId: Long) {

        companion object {
            fun forUser(id: Long): Param {
                return Param(id)
            }
        }
    }
}