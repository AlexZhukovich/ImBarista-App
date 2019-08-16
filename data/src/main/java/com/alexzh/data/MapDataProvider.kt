package com.alexzh.data

import com.alexzh.data.mapper.MapMapper
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.imbarista.domain.MapProvider
import com.alexzh.imbarista.domain.model.Map

class MapDataProvider(
    private val preferencesRepository: PreferencesRepository,
    private val mapMapper: MapMapper
) : MapProvider {

    override fun getMap(): Map {
        return mapMapper.mapFromEntity(preferencesRepository.getMapProvider())
    }
}