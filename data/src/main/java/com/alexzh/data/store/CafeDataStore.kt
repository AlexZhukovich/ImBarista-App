package com.alexzh.data.store

import com.alexzh.data.model.CafeEntity
import io.reactivex.Single

interface CafeDataStore {

    fun getCafes(
        currentLatitude: Double,
        currentLongitude: Double
    ): Single<List<CafeEntity>>
}