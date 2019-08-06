package com.alexzh.data.repository

import com.alexzh.data.model.SessionEntity

interface PreferencesRepository {

    fun saveSessionInfo(sessionEntity: SessionEntity)

    fun clearSessionInfo()

    fun getSessionInfo(): SessionEntity
}