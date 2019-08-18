package com.alexzh.imbarista.viewmodel

import android.app.Application
import android.content.ServiceConnection
import android.location.Location
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexzh.imbarista.domain.interactor.cafe.GetNearCafeFromTomTomSource
import com.alexzh.imbarista.domain.interactor.cafe.GetNearCafeFromTomTomSource.Param.*
import com.alexzh.imbarista.domain.model.Cafe
import com.alexzh.imbarista.mapper.CafeViewMapper
import com.alexzh.imbarista.mapper.MapViewMapper
import com.alexzh.imbarista.model.CafeView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.tomtom.online.sdk.common.location.LatLng
import com.tomtom.online.sdk.common.location.LatLngAcc
import com.tomtom.online.sdk.location.LocationSource
import com.tomtom.online.sdk.location.LocationUpdateListener
import com.tomtom.online.sdk.search.data.fuzzy.FuzzySearchQueryBuilder
import com.tomtom.online.sdk.search.extensions.SearchService
import com.tomtom.online.sdk.search.extensions.SearchServiceConnectionCallback
import com.tomtom.online.sdk.search.extensions.SearchServiceManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class TomTomMapViewModel (
    private val getNearCafeFromTomTomSource: GetNearCafeFromTomTomSource,
    private val cafeViewMapper: CafeViewMapper,
    application: Application
) : AndroidViewModel(application), /*SearchServiceConnectionCallback, */LocationUpdateListener {
    // TODO: should be used UseCase

//    companion object {
//        const val SEARCH_RADIUS = 1_000.0F
//        const val QUERY_TEXT = "cafe"
//    }

//    private var searchService: SearchService? = null
//    private var searchServiceConnection: ServiceConnection? = null
    private var locationSource: LocationSource? = null

    private val cafesLiveData: MutableLiveData<Resource<List<CafeView>>> = MutableLiveData()

    fun getCafes(): LiveData<Resource<List<CafeView>>> {
        return cafesLiveData
    }

    fun fetchCafes(locationSource: LocationSource) {
        cafesLiveData.postValue(Resource(ResourceState.ERROR, null, null))

        this.locationSource = locationSource
//        this.searchServiceConnection = SearchServiceManager.createAndBind(this.getApplication(), this)
        locationSource.addLocationUpdateListener(this)
    }

//    override fun onBindSearchService(searchService: SearchService?) {
//        if (searchService != null) {
//            this.searchService = searchService
//        }
//    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            locationSource?.removeLocationUpdateListener(this)

            getNearCafeFromTomTomSource.execute(
                GetNearCafeSubscriber(),
                GetNearCafeFromTomTomSource.Param.forCafeNearMe(location.latitude, location.longitude)
            )
//            val currentPosition = LatLng(location)
//            val query = FuzzySearchQueryBuilder(QUERY_TEXT)
//                .withCategory(true)
//                .withLimit(10)
//                .withPreciseness(LatLngAcc(currentPosition, SEARCH_RADIUS))
//                .build()
//
//            searchService?.search(query)
//                ?.subscribeOn(Schedulers.io())
//                ?.observeOn(AndroidSchedulers.mainThread())
//                ?.doOnError { error ->
//                    Log.e("TomTomMapFragment", error.message)
//                }
//                ?.subscribe { response ->
//                    if (response.results.isNotEmpty()) {
//                        val cafes = mutableListOf<CafeView>()
//                        response.results.forEach {
//                            cafes.add(CafeView(it.poi.name, it.position.latitude, it.position.longitude))
//                        }
//                        cafesLiveData.postValue(Resource(
//                            ResourceState.SUCCESS,
//                            cafes,
//                            null
//                        ))
//                    }
//                }
        }
    }

    override fun onCleared() {
        locationSource?.removeAllLocationUpdateListeners()
        locationSource = null
        getNearCafeFromTomTomSource.dispose()
//        SearchServiceManager.unbind(this.getApplication(), searchServiceConnection)
        super.onCleared()
    }

    private inner class GetNearCafeSubscriber : DisposableSingleObserver<List<Cafe>>() {
        override fun onSuccess(cafes: List<Cafe>) {
            cafesLiveData.postValue(Resource(
                ResourceState.SUCCESS,
                cafes.map { cafeViewMapper.mapToView(it) },
                null
            ))
        }

        override fun onError(error: Throwable) {
            cafesLiveData.postValue(Resource(
                ResourceState.ERROR,
                null,
                error
            ))
        }
    }
}