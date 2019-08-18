package com.alexzh.imbarista.remote.service

import android.app.Application
import android.content.ServiceConnection
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.location.LatLngAcc
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchResponse
import com.tomtom.online.sdk.search.extensions.SearchService
import com.tomtom.online.sdk.search.extensions.SearchServiceConnectionCallback
import com.tomtom.online.sdk.search.extensions.SearchServiceManager
import io.reactivex.Single
import java.lang.RuntimeException

class TomTomDataSearchService(
    application: Application
) : TomTomSearchService, SearchServiceConnectionCallback {

    companion object {
        const val SEARCH_RADIUS = 1_000.0F
        const val QUERY_TEXT = "cafe"
    }

    private var searchService: SearchService? = null
    private var searchServiceConnection: ServiceConnection? = null

    init {
        this.searchServiceConnection = SearchServiceManager.createAndBind(application, this)
    }

    override fun onBindSearchService(searchService: SearchService?) {
        if (searchService != null) {
            this.searchService = searchService
        }
    }

    override fun getCafeNearMe(
        currentLatitude: Double,
        currentLongitude: Double
    ): Single<FuzzySearchResponse> {
        val currentPosition = LatLng(currentLatitude, currentLongitude)
        val query = FuzzySearchQueryBuilder(QUERY_TEXT)
            .withCategory(true)
            .withLimit(10)
            .withPreciseness(LatLngAcc(currentPosition, SEARCH_RADIUS))
            .build()

        return (if (searchService == null) {
            Single.error(RuntimeException("TomTom search service is unavailable"))
        } else {
            searchService?.search(query)
        }) as Single<FuzzySearchResponse>
    }
}