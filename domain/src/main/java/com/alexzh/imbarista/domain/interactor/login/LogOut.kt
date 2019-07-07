package com.alexzh.imbarista.domain.interactor.login

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.CompletableUseCase
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Completable

class LogOut(
    private val userRepository: UserRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<Nothing>(postExecutionThread) {

    override fun buildCompletableUseCase(param: Nothing?): Completable {
        return userRepository.logOut()
    }
}