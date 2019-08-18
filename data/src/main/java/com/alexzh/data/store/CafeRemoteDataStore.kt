package com.alexzh.data.store

import com.alexzh.data.model.CafeEntity
import com.alexzh.data.repository.CafeRemoteRepository
import io.reactivex.Single

class CafeRemoteDataStore(
    private val repository: CafeRemoteRepository
) : CafeDataStore {

    override fun getCafes(
        currentLatitude: Double,
        currentLongitude: Double
    ): Single<List<CafeEntity>> {
        return repository.getCafes(currentLatitude, currentLongitude)
    }
}