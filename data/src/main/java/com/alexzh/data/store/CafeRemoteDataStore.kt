package com.alexzh.data.store

import com.alexzh.data.model.CafeEntity
import com.alexzh.data.repository.CafeRemoteRepository
import com.alexzh.data.repository.PreferencesRepository
import io.reactivex.Single

class CafeRemoteDataStore(
    private val repository: CafeRemoteRepository,
    private val preferencesRepository: PreferencesRepository
) : CafeDataStore {

    override fun getCafes(
        currentLatitude: Double,
        currentLongitude: Double
    ): Single<List<CafeEntity>> {
        val searchRadius = preferencesRepository.getSearchRadius()
        val numberCafesOnMap = preferencesRepository.getNumberCafesOnMap()
        return repository.getCafes(currentLatitude, currentLongitude, searchRadius, numberCafesOnMap)
    }
}