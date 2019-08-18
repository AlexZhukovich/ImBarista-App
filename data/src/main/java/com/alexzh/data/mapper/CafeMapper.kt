package com.alexzh.data.mapper

import com.alexzh.data.model.CafeEntity
import com.alexzh.imbarista.domain.model.Cafe

class CafeMapper : EntityMapper<CafeEntity, Cafe> {

    override fun mapFromEntity(entity: CafeEntity): Cafe {
        return Cafe(
            entity.title,
            entity.latitude,
            entity.longitude
        )
    }

    override fun mapToEntity(domainModel: Cafe): CafeEntity {
        return CafeEntity(
            domainModel.title,
            domainModel.latitude,
            domainModel.longitude
        )
    }
}