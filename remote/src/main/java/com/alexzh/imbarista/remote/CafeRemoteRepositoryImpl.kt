package com.alexzh.imbarista.remote

import com.alexzh.data.model.CafeEntity
import com.alexzh.data.repository.CafeRemoteRepository
import com.alexzh.imbarista.remote.mapper.CafeMapper
import com.alexzh.imbarista.remote.model.CafeModel
import com.alexzh.imbarista.remote.service.TomTomSearchService
import io.reactivex.Single
import java.lang.RuntimeException

class CafeRemoteRepositoryImpl(
    private val tomTomSearchService: TomTomSearchService,
    private val cafeMapper: CafeMapper
) : CafeRemoteRepository {
    override fun getCafes(
        currentLatitude: Double,
        currentLongitude: Double,
        searchRadius: Int,
        numberCafesOnMap: Int
    ): Single<List<CafeEntity>> {
        return tomTomSearchService.getCafeNearMe(currentLatitude, currentLongitude, searchRadius, numberCafesOnMap)
            .onErrorResumeNext { Single.error(RuntimeException(it)) }
            .map { response ->
                response.results.map {
                    cafeMapper.mapFromModel(
                        CafeModel(
                            it.poi.name,
                            it.position.latitude,
                            it.position.longitude
                        )
                    )
                }
            }
    }
}