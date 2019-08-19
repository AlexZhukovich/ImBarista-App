package com.alexzh.imbarista.domain.repository

import com.alexzh.imbarista.domain.model.Cafe
import io.reactivex.Single

interface NearMeCafeRepository {

    fun getCafes(
        currentLatitude: Double,
        currentLongitude: Double
    ): Single<List<Cafe>>
}