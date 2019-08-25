package com.alexzh.data

import com.alexzh.data.mapper.MapDataMapper
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.imbarista.domain.MapProvider
import com.alexzh.imbarista.domain.model.Map

class MapDataProvider(
    private val preferencesRepository: PreferencesRepository,
    private val mapDataMapper: MapDataMapper
) : MapProvider {

    override fun getMap(): Map {
        return mapDataMapper.mapFromEntity(preferencesRepository.getMapProvider())
    }
}