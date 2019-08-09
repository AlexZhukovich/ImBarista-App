package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Single

class GetExistingSession(
    private val userRepository: UserRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Session, Nothing>(postExecutionThread) {

    override fun buildSingleUseCase(param: Nothing?): Single<Session> {
        return userRepository.getExistingSession()
    }
}