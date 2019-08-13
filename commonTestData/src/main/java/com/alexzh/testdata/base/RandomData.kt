package com.alexzh.testdata.base

import java.util.*

object RandomData {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomEmail(): String = "${UUID.randomUUID()}@mail.com"

    fun randomLong(): Long = Math.random().toLong()

    fun randomBoolean(): Boolean = Math.random().toLong() > 0.5

    fun randomYN(): String = if (Math.random().toLong() > 0.5) "Y" else "N"
}