package com.alexzh.data.mapper

import com.alexzh.data.model.SessionEntity
import com.alexzh.imbarista.domain.model.Session

class SessionDataMapper : EntityMapper<SessionEntity, Session> {

    override fun mapFromEntity(entity: SessionEntity): Session {
        return Session(
            entity.sessionId,
            entity.accessToken,
            entity.accessTokenExpiry,
            entity.refreshToken,
            entity.refreshTokenExpiry
        )
    }

    override fun mapToEntity(domainModel: Session): SessionEntity {
        return SessionEntity(
            domainModel.sessionId,
            domainModel.accessToken,
            domainModel.accessTokenExpiry,
            domainModel.refreshToken,
            domainModel.refreshTokenExpiry
        )
    }
}