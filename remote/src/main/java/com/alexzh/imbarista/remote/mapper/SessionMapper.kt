package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.SessionEntity
import com.alexzh.imbarista.remote.model.SessionModel

class SessionMapper : ModelMapper<SessionModel, SessionEntity> {

    override fun mapFromModel(model: SessionModel): SessionEntity {
        return SessionEntity(
            model.sessionId,
            model.accessToken,
            model.accessTokenExpiry,
            model.refreshToken,
            model.refreshTokenExpiry
        )
    }
}