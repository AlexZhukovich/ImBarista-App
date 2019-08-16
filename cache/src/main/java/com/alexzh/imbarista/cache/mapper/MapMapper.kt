package com.alexzh.imbarista.cache.mapper

import com.alexzh.data.model.MapEntity
import com.alexzh.imbarista.cache.model.Map

class MapMapper : CachedMapper<Map, MapEntity> {

    override fun mapFromCached(cacheModel: Map): MapEntity {
        return MapEntity.valueOf(cacheModel.name)
    }

    override fun mapToCached(entityModel: MapEntity): Map {
        return Map.valueOf(entityModel.name)
    }
}