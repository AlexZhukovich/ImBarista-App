package com.alexzh.data.repository

import com.alexzh.data.model.SessionEntity

interface PreferencesRepository {

    fun getSessionInfo(): SessionEntity

    fun saveSessionInfo(sessionEntity: SessionEntity)

    fun clearSessionInfo()
}