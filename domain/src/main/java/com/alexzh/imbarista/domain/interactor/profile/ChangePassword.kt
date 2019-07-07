package com.alexzh.imbarista.domain.interactor.profile

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.CompletableUseCase
import com.alexzh.imbarista.domain.repository.ProfileRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException

class ChangePassword(
    private val profileRepository: ProfileRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<ChangePassword.Param>(postExecutionThread) {

    override fun buildCompletableUseCase(param: Param?): Completable {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return profileRepository.changePassword(param.newPassword)
    }

    data class Param(val newPassword: String) {
        companion object {
            fun forChangingPassword(newPassword: String): Param {
                return Param(newPassword)
            }
        }
    }
}