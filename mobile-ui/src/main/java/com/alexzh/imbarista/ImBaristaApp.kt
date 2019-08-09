package com.alexzh.imbarista

import android.app.Application
import com.alexzh.imbarista.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ImBaristaApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    private fun initDI() {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@ImBaristaApp)
            modules(listOf(
                executorModule,
                mapperModule,
                dataModule,
                useCaseModule,
                viewModelModule
            ))
        }
    }
}