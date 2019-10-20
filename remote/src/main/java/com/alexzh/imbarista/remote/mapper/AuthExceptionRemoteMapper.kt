package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.exception.AuthDataException
import retrofit2.HttpException

class AuthExceptionRemoteMapper : ExceptionMapper<HttpException, AuthDataException> {

    override fun mapToDataException(remoteException: HttpException): AuthDataException {
        return AuthDataException()
    }
}