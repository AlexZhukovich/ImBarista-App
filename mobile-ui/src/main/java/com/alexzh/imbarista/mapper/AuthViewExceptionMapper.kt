package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.exception.AuthException
import com.alexzh.imbarista.exception.AuthViewException

class AuthViewExceptionMapper : ExceptionMapper<AuthViewException, AuthException> {

    override fun mapToView(type: AuthException): AuthViewException {
        return AuthViewException()
    }
}