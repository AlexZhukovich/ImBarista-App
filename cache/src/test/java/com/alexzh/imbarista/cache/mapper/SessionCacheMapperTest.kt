package com.alexzh.imbarista.cache.mapper

import com.alexzh.data.model.SessionEntity
import com.alexzh.imbarista.cache.model.Session
import com.alexzh.imbarista.commonandroidtestdata.cache.GenerateCacheTestData.generateSession
import com.alexzh.testdata.data.GenerateDataTestData.generateSessionEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class SessionCacheMapperTest {

    private val mapper = SessionCacheMapper()

    @Test
    fun mapFromEntityToCacheModelCorrectly() {
        val sessionEntity = generateSessionEntity()
        val sessionCacheModel = mapper.mapToCached(sessionEntity)

        assertEqualsData(sessionCacheModel, sessionEntity)
    }

    @Test
    fun mapFromCacheModelToEntityCorrectly() {
        val sessionCacheModel = generateSession()
        val sessionEntity = mapper.mapFromCached(sessionCacheModel)

        assertEqualsData(sessionCacheModel, sessionEntity)
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