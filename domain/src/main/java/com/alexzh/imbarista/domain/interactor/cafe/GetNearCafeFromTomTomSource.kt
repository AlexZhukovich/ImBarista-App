package com.alexzh.imbarista.domain.interactor.cafe

import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.SingleUseCase
import com.alexzh.imbarista.domain.model.Cafe
import com.alexzh.imbarista.domain.repository.NearMeCafeRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException

class GetNearCafeFromTomTomSource(
    private val nearMeCafeRepository: NearMeCafeRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Cafe>, GetNearCafeFromTomTomSource.Param>(postExecutionThread) {

    override fun buildSingleUseCase(param: Param?): Single<List<Cafe>> {
        if (param == null) throw IllegalArgumentException("Param can't be null")
        return nearMeCafeRepository.getCafes(param.currentLatitude, param.currentLongitude)
    }

    data class Param(
        val currentLatitude: Double,
        val currentLongitude: Double
    ) {
        companion object {
            fun forCafeNearMe(
                currentLatitude: Double,
                currentLongitude: Double
            ): Param {
                return Param(currentLatitude, currentLongitude)
            }
        }
    }
}