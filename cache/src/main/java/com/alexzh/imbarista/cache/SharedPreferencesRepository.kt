package com.alexzh.imbarista.cache

import android.content.SharedPreferences
import com.alexzh.data.model.SessionEntity
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.imbarista.cache.mapper.SessionMapper
import com.alexzh.imbarista.cache.model.Session

class SharedPreferencesRepository(
    private val prefs: SharedPreferences,
    private val sessionMapper: SessionMapper
) : PreferencesRepository {

    companion object {
        private const val STR_DEFAULT_VALUE = ""
        private const val LONG_DEFAULT_VALUE = -1L

        private const val SESSION_ID = "session_id"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val ACCESS_TOKEN_EXPIRY = "access_token_expiry"
        private const val REFRESH_TOKEN_EXPIRY = "refresh_token_expiry"
    }

    override fun saveSessionInfo(sessionEntity: SessionEntity) {
        val session = sessionMapper.mapToCached(sessionEntity)
        prefs.edit()
            .putLong(SESSION_ID, session.sessionId)
            .putString(ACCESS_TOKEN, session.accessToken)
            .putLong(ACCESS_TOKEN_EXPIRY, session.accessTokenExpiry)
            .putString(REFRESH_TOKEN, session.refreshToken)
            .putLong(REFRESH_TOKEN_EXPIRY, session.refreshTokenExpiry)
            .apply()
    }

    override fun clearSessionInfo() {
        prefs.edit()
            .remove(SESSION_ID)
            .remove(ACCESS_TOKEN)
            .remove(ACCESS_TOKEN_EXPIRY)
            .remove(REFRESH_TOKEN)
            .remove(REFRESH_TOKEN_EXPIRY)
            .apply()
    }

    override fun getSessionInfo(): SessionEntity {
        return sessionMapper.mapFromCached(
            Session(
                prefs.getLong(SESSION_ID, LONG_DEFAULT_VALUE),
                prefs.getString(ACCESS_TOKEN, STR_DEFAULT_VALUE) as String,
                prefs.getLong(ACCESS_TOKEN_EXPIRY, LONG_DEFAULT_VALUE),
                prefs.getString(REFRESH_TOKEN, STR_DEFAULT_VALUE) as String,
                prefs.getLong(REFRESH_TOKEN_EXPIRY, LONG_DEFAULT_VALUE)
            )
        )
    }
}