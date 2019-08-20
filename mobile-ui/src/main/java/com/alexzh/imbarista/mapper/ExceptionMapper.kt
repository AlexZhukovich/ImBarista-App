package com.alexzh.imbarista.mapper

interface ExceptionMapper<out V, in D> {

    fun mapToView(type: D): V
}