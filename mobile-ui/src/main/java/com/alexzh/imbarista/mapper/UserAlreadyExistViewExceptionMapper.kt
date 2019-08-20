package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.UserAlreadyExistException
import com.alexzh.imbarista.model.UserAlreadyExistViewException

class UserAlreadyExistViewExceptionMapper : ExceptionMapper<UserAlreadyExistViewException, UserAlreadyExistException> {

    override fun mapToView(type: UserAlreadyExistException): UserAlreadyExistViewException {
        return UserAlreadyExistViewException()
    }
}