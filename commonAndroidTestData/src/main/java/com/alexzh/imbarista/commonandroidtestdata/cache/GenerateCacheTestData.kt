package com.alexzh.imbarista.commonandroidtestdata.cache

import com.alexzh.imbarista.cache.model.Session
import com.alexzh.imbarista.cache.model.User
import com.alexzh.testdata.base.RandomData

object GenerateCacheTestData {

    fun generateSession(): Session {
        return Session(
            sessionId = RandomData.randomLong(),
            accessToken = RandomData.randomString(),
            accessTokenExpiry = RandomData.randomLong(),
            refreshToken = RandomData.randomString(),
            refreshTokenExpiry = RandomData.randomLong()
        )
    }

    fun generateUser(): User {
        return User(
            id = RandomData.randomLong(),
            name = RandomData.randomString(),
            email = RandomData.randomEmail()
        )
    }
}