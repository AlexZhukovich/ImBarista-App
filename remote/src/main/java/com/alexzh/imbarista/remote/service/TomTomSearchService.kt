package com.alexzh.imbarista.remote.service

import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse
import io.reactivex.Single

interface TomTomSearchService {

    fun getCafeNearMe(
        currentLatitude: Double,
        currentLongitude: Double
    ): Single<FuzzySearchResponse>
}