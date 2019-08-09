package com.alexzh.data.repository

import com.alexzh.data.model.SessionEntity
import com.alexzh.data.model.UserEntity

interface PreferencesRepository {

    fun getSessionInfo(): SessionEntity

    fun saveSessionInfo(sessionEntity: SessionEntity)

    fun clearSessionInfo()

    fun getUserInfo(): UserEntity

    fun saveUserEntity(userEntity: UserEntity)

    fun clearUserInfo()
}