package com.alexzh.imbarista.state

class Resource<out T>(
    val status: ResourceState,
    val data: T?,
    val error: Throwable?
)