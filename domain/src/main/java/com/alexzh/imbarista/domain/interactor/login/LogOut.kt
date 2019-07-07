package com.alexzh.imbarista.domain.interactor.login

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.CompletableUseCase
import com.alexzh.imbarista.domain.repository.AuthRepository
import io.reactivex.Completable

class LogOut(
    private val authRepository: AuthRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Nothing>(postExecutionThread) {

    override fun buildCompletableUseCase(params: Nothing?): Completable {
        return authRepository.logOut()
    }
}