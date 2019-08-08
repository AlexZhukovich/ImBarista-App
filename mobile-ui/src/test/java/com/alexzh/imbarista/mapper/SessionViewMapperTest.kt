package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.model.SessionView
import com.alexzh.testdata.domain.GenerateDomainTestData.generateSession
import org.junit.Assert.assertEquals
import org.junit.Test

class SessionViewMapperTest {

    private val mapper = SessionViewModelMapper()

    @Test
    fun mapToViewMapsDataCorrectly() {
        val session = generateSession()
        val sessionView = mapper.mapToView(session)

        assertEqualsData(session, sessionView)
    }

    private fun assertEqualsData(
        session: Session,
        sessionView: SessionView
    ) {
        assertEquals(session.sessionId, sessionView.sessionId)
        assertEquals(session.accessToken, sessionView.accessToken)
        assertEquals(session.accessTokenExpiry, sessionView.accessTokenExpiry)
        assertEquals(session.refreshToken, sessionView.refreshToken)
        assertEquals(session.refreshTokenExpiry, sessionView.refreshTokenExpiry)
    }
}