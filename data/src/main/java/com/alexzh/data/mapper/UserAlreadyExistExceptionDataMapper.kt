package com.alexzh.data.mapper

import com.alexzh.data.model.UserAlreadyExistDataException
import com.alexzh.imbarista.domain.model.UserAlreadyExistException

class UserAlreadyExistExceptionDataMapper : ExceptionMapper<UserAlreadyExistDataException, UserAlreadyExistException> {

    override fun mapFromEntityException(entity: UserAlreadyExistDataException): UserAlreadyExistException {
        return UserAlreadyExistException()
    }
}