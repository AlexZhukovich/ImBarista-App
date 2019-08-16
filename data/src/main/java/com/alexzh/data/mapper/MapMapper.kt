package com.alexzh.data.mapper

import com.alexzh.data.model.MapEntity
import com.alexzh.imbarista.domain.model.Map

class MapMapper : EntityMapper<MapEntity, Map> {

    override fun mapFromEntity(entity: MapEntity): Map {
        return Map.valueOf(entity.name)
    }

    override fun mapToEntity(domainModel: Map): MapEntity {
        return MapEntity.valueOf(domainModel.name)
    }
}