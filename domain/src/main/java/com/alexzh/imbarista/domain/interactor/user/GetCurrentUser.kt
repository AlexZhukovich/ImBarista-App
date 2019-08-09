package com.alexzh.imbarista.domain.interactor.user

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.User
import com.alexzh.imbarista.domain.repository.UserRepository
import io.reactivex.Single

class GetCurrentUser(
    private val userRepository: UserRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<User, Nothing>(postExecutionThread) {

    override fun buildSingleUseCase(param: Nothing?): Single<User> {
        return userRepository.getCurrentUserInfo()
    }
}