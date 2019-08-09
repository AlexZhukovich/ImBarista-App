package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.model.SessionView

class SessionViewMapper : Mapper<SessionView, Session> {

    override fun mapToView(type: Session): SessionView {
        return SessionView(
            type.sessionId,
            type.accessToken,
            type.accessTokenExpiry,
            type.refreshToken,
            type.refreshTokenExpiry
        )
    }
}