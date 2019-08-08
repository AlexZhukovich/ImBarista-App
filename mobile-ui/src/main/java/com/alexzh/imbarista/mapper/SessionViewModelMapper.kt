package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.Session
import com.alexzh.imbarista.model.SessionViewModel

class SessionViewModelMapper : Mapper<SessionViewModel, Session> {

    override fun mapToView(type: Session): SessionViewModel {
        return SessionViewModel(
            type.sessionId,
            type.accessToken,
            type.accessTokenExpiry,
            type.refreshToken,
            type.refreshTokenExpiry
        )
    }
}