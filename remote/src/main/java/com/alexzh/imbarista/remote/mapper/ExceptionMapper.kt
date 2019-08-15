package com.alexzh.imbarista.remote.mapper

interface ExceptionMapper<R, D> {
    fun mapToDataException(remoteException: R): D
}