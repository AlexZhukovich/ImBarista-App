package com.alexzh.data.repository

import com.alexzh.data.model.CafeEntity
import io.reactivex.Single

interface CafeRemoteRepository {
    fun getCafes(
        currentLatitude: Double,
        currentLongitude: Double
    ): Single<List<CafeEntity>>
}