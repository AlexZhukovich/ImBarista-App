package com.alexzh.imbarista.remote.mapper

import com.alexzh.data.model.CafeEntity
import com.alexzh.imbarista.remote.model.CafeModel

class CafeMapper : ModelMapper<CafeModel, CafeEntity> {

    override fun mapFromModel(model: CafeModel): CafeEntity {
        return CafeEntity(
            model.title,
            model.latitude,
            model.longitude
        )
    }
}