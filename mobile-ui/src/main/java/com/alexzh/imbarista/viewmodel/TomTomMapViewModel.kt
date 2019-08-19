package com.alexzh.imbarista.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.alexzh.imbarista.domain.interactor.cafe.GetNearCafeFromTomTomSource
import com.alexzh.imbarista.domain.model.Cafe
import com.alexzh.imbarista.mapper.CafeViewMapper
import com.alexzh.imbarista.model.CafeView
import com.alexzh.imbarista.state.Resource
import com.alexzh.imbarista.state.ResourceState
import com.tomtom.online.sdk.location.LocationSource
import com.tomtom.online.sdk.location.LocationUpdateListener
import io.reactivex.observers.DisposableSingleObserver

class TomTomMapViewModel(
    private val getNearCafeFromTomTomSource: GetNearCafeFromTomTomSource,
    private val cafeViewMapper: CafeViewMapper,
    application: Application
) : AndroidViewModel(application), LocationUpdateListener {

    private var locationSource: LocationSource? = null
    private val cafesLiveData: MutableLiveData<Resource<List<CafeView>>> = MutableLiveData()

    fun getCafes(): LiveData<Resource<List<CafeView>>> {
        return cafesLiveData
    }

    fun fetchCafes(locationSource: LocationSource) {
        cafesLiveData.postValue(Resource(ResourceState.LOADING, null, null))

        this.locationSource = locationSource
        locationSource.addLocationUpdateListener(this)
    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) {
            locationSource?.removeLocationUpdateListener(this)

            getNearCafeFromTomTomSource.execute(
                GetNearCafeSubscriber(),
                GetNearCafeFromTomTomSource.Param.forCafeNearMe(location.latitude, location.longitude)
            )
        }
    }

    override fun onCleared() {
        locationSource?.removeAllLocationUpdateListeners()
        locationSource = null
        getNearCafeFromTomTomSource.dispose()
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