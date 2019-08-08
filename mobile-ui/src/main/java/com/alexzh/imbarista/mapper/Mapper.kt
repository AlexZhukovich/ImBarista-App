package com.alexzh.imbarista.mapper

interface Mapper<out V, in D> {

    fun mapToView(type: D): V
}