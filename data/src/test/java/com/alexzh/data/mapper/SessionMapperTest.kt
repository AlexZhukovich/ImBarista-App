package com.alexzh.data.mapper

import com.alexzh.data.model.SessionEntity
import com.alexzh.imbarista.domain.model.Session
import com.alexzh.testdata.data.GenerateDataTestData.generateSessionEntity
import com.alexzh.testdata.domain.GenerateDomainTestData.generateSession
import org.junit.Assert.assertEquals
import org.junit.Test

class SessionMapperTest {

    private val mapper = SessionMapper()

    @Test
    fun mapFromEntityToDomainModelCorrectly() {
        val sessionEntity = generateSessionEntity()
        val session = mapper.mapFromEntity(sessionEntity)

        assertEqualsData(session, sessionEntity)
    }

    @Test
    fun mapFromDomainModelToEntityCorrectly() {
        val session = generateSession()
        val sessionEntity = mapper.mapToEntity(session)

        assertEqualsData(session, sessionEntity)
    }

    private fun assertEqualsData(
        session: Session,
        sessionEntity: SessionEntity
    ) {
        assertEquals(session.sessionId, sessionEntity.sessionId)
        assertEquals(session.accessToken, sessionEntity.accessToken)
        assertEquals(session.accessTokenExpiry, sessionEntity.accessTokenExpiry)
        assertEquals(session.refreshToken, sessionEntity.refreshToken)
        assertEquals(session.refreshTokenExpiry, sessionEntity.refreshTokenExpiry)
    }
}
