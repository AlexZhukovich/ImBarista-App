package com.alexzh.imbarista.mapper

import com.alexzh.imbarista.domain.model.Cafe
import com.alexzh.imbarista.model.CafeView

class CafeViewMapper : Mapper<CafeView, Cafe> {

    override fun mapToView(type: Cafe): CafeView {
        return CafeView(
            type.title,
            type.latitude,
            type.longitude
        )
    }
}