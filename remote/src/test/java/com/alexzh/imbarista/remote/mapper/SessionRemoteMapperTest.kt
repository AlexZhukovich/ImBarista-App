package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.SessionEntity
import com.alexzh.imbarista.remote.model.SessionModel
import com.alexzh.imbarista.commonandroidtestdata.remote.GenerateRemoteTestData.generateSessionModel
import org.junit.Assert
import org.junit.Test

class SessionRemoteMapperTest {

    private val mapper = SessionRemoteMapper()

    @Test
    fun mapFromModelMapsDataCorrectly() {
        val sessionModel = generateSessionModel()
        val sessionEntity = mapper.mapFromModel(sessionModel)

        assertEqualsData(sessionModel, sessionEntity)
    }

    private fun assertEqualsData(
        sessionModel: SessionModel,
        sessionEntity: SessionEntity
    ) {
        Assert.assertEquals(sessionModel.sessionId, sessionEntity.sessionId)
        Assert.assertEquals(sessionModel.accessToken, sessionEntity.accessToken)
        Assert.assertEquals(sessionModel.accessTokenExpiry, sessionEntity.accessTokenExpiry)
        Assert.assertEquals(sessionModel.refreshToken, sessionEntity.refreshToken)
        Assert.assertEquals(sessionModel.refreshTokenExpiry, sessionEntity.refreshTokenExpiry)
    }
}