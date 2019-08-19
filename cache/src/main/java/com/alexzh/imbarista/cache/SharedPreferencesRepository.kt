package com.alexzh.imbarista.cache

import android.content.SharedPreferences
import com.alexzh.data.model.MapEntity
import com.alexzh.data.model.SessionEntity
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.imbarista.cache.mapper.MapMapper
import com.alexzh.imbarista.cache.mapper.SessionMapper
import com.alexzh.imbarista.cache.model.Map
import com.alexzh.imbarista.cache.model.Session

class SharedPreferencesRepository(
    private val prefs: SharedPreferences,
    private val sessionMapper: SessionMapper,
    private val mapMapper: MapMapper
) : PreferencesRepository {

    companion object {
        private const val STR_DEFAULT_VALUE = ""
        private const val LONG_DEFAULT_VALUE = -1L

        private const val CAFE_SEARCH_RADIUS = "search_radius"
        private const val NUMBER_CAFES_ON_MAP = "number_cafes_on_map"

        private const val MAP_PROVIDER = "map_provider"

        private const val SESSION_ID = "session_id"
        private const val ACCESS_TOKEN = "access_token"
        private const val REFRESH_TOKEN = "refresh_token"
        private const val ACCESS_TOKEN_EXPIRY = "access_token_expiry"
        private const val REFRESH_TOKEN_EXPIRY = "refresh_token_expiry"
    }

    override fun getSearchRadius(): Int {
        return (prefs.getString(CAFE_SEARCH_RADIUS, STR_DEFAULT_VALUE) as String).toInt()

    }

    override fun saveDefaultSearchRadius(searchRadius: Int) {
        prefs.edit()
            .putString(CAFE_SEARCH_RADIUS, searchRadius.toString())
            .apply()
    }

    override fun getNumberCafesOnMap(): Int {
        return (prefs.getString(NUMBER_CAFES_ON_MAP, STR_DEFAULT_VALUE) as String).toInt()
    }

    override fun saveDefaultNumberCafesOnMap(numberCafesOnMap: Int) {
        prefs.edit()
            .putString(NUMBER_CAFES_ON_MAP, numberCafesOnMap.toString())
            .apply()
    }

    override fun getMapProvider(): MapEntity {
        val mapValue = prefs.getString(MAP_PROVIDER, STR_DEFAULT_VALUE) as String
        return mapMapper.mapFromCached(
            if (mapValue.isBlank())
                Map.TOMTOM
            else
                Map.valueOf(mapValue)
        )
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
}