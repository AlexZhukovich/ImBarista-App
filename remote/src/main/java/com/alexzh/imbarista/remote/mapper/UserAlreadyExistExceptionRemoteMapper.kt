package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.UserAlreadyExistDataException
import retrofit2.HttpException

class UserAlreadyExistExceptionRemoteMapper : ExceptionMapper<HttpException, UserAlreadyExistDataException> {

    override fun mapToDataException(remoteException: HttpException): UserAlreadyExistDataException {
        return UserAlreadyExistDataException()
    }

}