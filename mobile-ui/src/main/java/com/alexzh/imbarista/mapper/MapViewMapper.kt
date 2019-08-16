package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.Map
import com.alexzh.imbarista.model.MapView

class MapViewMapper : Mapper<MapView, Map> {
    override fun mapToView(type: Map): MapView {
        return MapView.valueOf(type.name)
    }
}