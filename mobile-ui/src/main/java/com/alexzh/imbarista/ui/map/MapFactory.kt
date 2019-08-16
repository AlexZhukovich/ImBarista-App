package com.alexzh.imbarista.ui.map

import androidx.fragment.app.Fragment
import com.alexzh.imbarista.domain.MapProvider
import com.alexzh.imbarista.mapper.MapViewMapper
import com.alexzh.imbarista.model.MapView

class MapFactory(
    private val mapProvider: MapProvider,
    private val mapViewMapper: MapViewMapper
) {
    fun getMap(): Fragment {
        return if (mapViewMapper.mapToView(mapProvider.getMap()) == MapView.GOOGLE) {
            GoogleMapFragment()
        } else {
            TomTomMapFragment()
        }
    }
}