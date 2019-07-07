package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.CompletableUseCase
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException

class ChangePassword(
    private val userRepository: UserRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<ChangePassword.Param>(postExecutionThread) {

    override fun buildCompletableUseCase(param: Param?): Completable {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return userRepository.changePassword(param.userId, param.newPassword)
    }

    data class Param(val userId: Long, val newPassword: String) {
        companion object {
            fun forChangingPassword(
                userId: Long,
                newPassword: String
            ): Param {
                return Param(userId, newPassword)
            }
        }
    }
}