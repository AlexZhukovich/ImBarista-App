package com.alexzh.data

import com.alexzh.data.mapper.CafeDataMapper
import com.alexzh.data.store.CafeDataStore
import com.alexzh.imbarista.domain.model.Cafe
import com.alexzh.imbarista.domain.repository.NearMeCafeRepository
import io.reactivex.Single

class NearMeCafeDataRepository(
    private val cafeDataStore: CafeDataStore,
    private val cafeDataMapper: CafeDataMapper
) : NearMeCafeRepository {

    override fun getCafes(
        currentLatitude: Double,
        currentLongitude: Double
    ): Single<List<Cafe>> {
        return cafeDataStore.getCafes(currentLatitude, currentLongitude)
            .map { cafes -> cafes.map { cafeDataMapper.mapFromEntity(it) } }
    }
}