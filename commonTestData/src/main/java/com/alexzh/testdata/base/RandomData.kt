package com.alexzh.testdata.base

import java.util.UUID

object RandomData {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomEmail(): String = "${UUID.randomUUID()}@mail.com"

    fun randomLong(): Long = Math.random().toLong()
}