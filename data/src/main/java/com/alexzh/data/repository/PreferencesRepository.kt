package com.alexzh.data.repository

import com.alexzh.data.model.MapEntity
import com.alexzh.data.model.SessionEntity

interface PreferencesRepository {

    fun getSearchRadius(): Int

    fun saveDefaultSearchRadius(searchRadius: Int)

    fun getNumberCafesOnMap(): Int

    fun saveDefaultNumberCafesOnMap(numberCafesOnMap: Int)

    fun getMapProvider(): MapEntity

    fun getSessionInfo(): SessionEntity

    fun saveSessionInfo(sessionEntity: SessionEntity)

    fun clearSessionInfo()
}