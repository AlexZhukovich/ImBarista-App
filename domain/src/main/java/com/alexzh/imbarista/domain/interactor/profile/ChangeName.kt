package com.alexzh.imbarista.domain.interactor.profile

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.CompletableUseCase
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException

class ChangeName(
    private val userRepository: UserRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<ChangeName.Param>(postExecutionThread) {

    override fun buildCompletableUseCase(param: Param?): Completable {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return userRepository.changeName(param.newName)
    }

    data class Param(val newName: String) {
        companion object {
            fun forChangingName(newName: String): Param {
                return Param(newName)
            }
        }
    }
}