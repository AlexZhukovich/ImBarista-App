package com.alexzh.data.mapper

import com.alexzh.data.exception.AuthDataException
import com.alexzh.imbarista.domain.exception.AuthException

class AuthExceptionMapper : ExceptionMapper<AuthDataException, AuthException> {

    override fun mapFromEntityException(entity: AuthDataException): AuthException {
        return AuthException()
    }
}