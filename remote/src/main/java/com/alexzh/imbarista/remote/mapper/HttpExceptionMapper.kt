package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.HttpDataException
import retrofit2.HttpException

class HttpExceptionMapper : ExceptionMapper<HttpException, HttpDataException> {
    override fun mapToDataException(remoteException: HttpException): HttpDataException {
        return HttpDataException(
            remoteException.code(),
            remoteException.message()
        )
    }
}