package com.alexzh.imbarista.remote.model

class ResponseModel<T>(
    statusCode: Int,
    success: Boolean,
    messages: List<String>,
    data: T
)