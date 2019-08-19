package com.alexzh.imbarista

import android.app.Application
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.imbarista.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ImBaristaApp : Application() {

    private val preferencesRepository by inject<PreferencesRepository>()

    override fun onCreate() {
        super.onCreate()
        initDI()
        initDefaultValues()
    }

    private fun initDefaultValues() {
        val searchRadius = getString(R.string.pref_cafe_search_radius_default_value).toInt()
        val numberCafesOnMap = getString(R.string.pref_cafe_search_cafe_limits_on_map_default_value).toInt()

        preferencesRepository.saveDefaultSearchRadius(searchRadius)
        preferencesRepository.saveDefaultNumberCafesOnMap(numberCafesOnMap)
    }

    private fun initDI() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ImBaristaApp)
            modules(listOf(
                executorModule,
                mapperModule,
                dataModule,
                mapProviderModule,
                useCaseModule,
                viewModelModule
            ))
        }
    }
}