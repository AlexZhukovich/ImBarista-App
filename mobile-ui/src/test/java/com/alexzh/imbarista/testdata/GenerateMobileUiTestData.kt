package com.alexzh.imbarista.testdata

import com.alexzh.imbarista.model.SessionView
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
}