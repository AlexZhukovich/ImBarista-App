package com.alexzh.imbarista.remote.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class CoffeeDrinksServiceFactory {

    companion object {
        const val BASE_URL = "https://api.coffee-drinks.alexzh.com/v1/"
    }

    fun createCoffeeDrinksService(isDebug: Boolean): CoffeeDrinksService {
        val okHttpClient = createOkHttpClient(createLoggingInterceptor(isDebug))
        return createCoffeeDrinksService(okHttpClient)
    }

    private fun createCoffeeDrinksService(okHttpClient: OkHttpClient): CoffeeDrinksService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CoffeeDrinksService::class.java)
    }

    private fun createOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .hostnameVerifier { _, _ -> true }
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor { chain ->
                var tryCount = 0
                val maxLimit = 5
                val request = chain.request()
                var response = chain.proceed(request)

                while (!response.isSuccessful && tryCount < maxLimit) {
                    tryCount++
                    response.close()
                    response = chain.proceed(request)
                }

                response
            }
            .build()
    }

    private fun createLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}