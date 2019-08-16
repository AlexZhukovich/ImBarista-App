package com.alexzh.data.repository

import com.alexzh.data.model.MapEntity
import com.alexzh.data.model.SessionEntity

interface PreferencesRepository {

    fun getMapProvider(): MapEntity

    fun getSessionInfo(): SessionEntity

    fun saveSessionInfo(sessionEntity: SessionEntity)

    fun clearSessionInfo()
}