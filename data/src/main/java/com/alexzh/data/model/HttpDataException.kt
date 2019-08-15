package com.alexzh.data.model

import java.lang.RuntimeException

class HttpDataException(
    val code: Int,
    message: String
) : RuntimeException(message)