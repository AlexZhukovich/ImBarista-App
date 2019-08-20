package com.alexzh.data.mapper

interface ExceptionMapper<E, D> {

    fun mapFromEntityException(entity: E): D
}