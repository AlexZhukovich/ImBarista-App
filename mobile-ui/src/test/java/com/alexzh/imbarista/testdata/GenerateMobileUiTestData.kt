package com.alexzh.imbarista.testdata

import com.alexzh.imbarista.model.SessionView
import com.alexzh.imbarista.model.UserView
import com.alexzh.testdata.base.RandomData.randomEmail
import com.alexzh.testdata.base.RandomData.randomLong
import com.alexzh.testdata.base.RandomData.randomString

object GenerateMobileUiTestData {

    fun generateSessionView(): SessionView {
        return SessionView(
            sessionId = randomLong(),
            accessToken = randomString(),
            accessTokenExpiry = randomLong(),
            refreshToken = randomString(),
            refreshTokenExpiry = randomLong()
        )
    }

    fun generateUserView(): UserView {
        return UserView(
            id = randomLong(),
            name = randomString(),
            email = randomEmail()
        )
    }
}