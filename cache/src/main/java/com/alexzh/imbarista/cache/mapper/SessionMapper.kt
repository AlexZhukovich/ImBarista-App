package com.alexzh.imbarista.cache.mapper

import com.alexzh.data.model.SessionEntity
import com.alexzh.imbarista.cache.model.Session

class SessionMapper : CachedMapper<Session, SessionEntity> {

    override fun mapFromCached(cacheModel: Session): SessionEntity {
        return SessionEntity(
            cacheModel.sessionId,
            cacheModel.accessToken,
            cacheModel.accessTokenExpiry,
            cacheModel.refreshToken,
            cacheModel.refreshTokenExpiry
        )
    }

    override fun mapToCached(entityModel: SessionEntity): Session {
        return Session(
            entityModel.sessionId,
            entityModel.accessToken,
            entityModel.accessTokenExpiry,
            entityModel.refreshToken,
            entityModel.refreshTokenExpiry
        )
    }
}