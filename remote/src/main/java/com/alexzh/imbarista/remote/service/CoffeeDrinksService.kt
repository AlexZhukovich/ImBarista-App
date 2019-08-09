package com.alexzh.imbarista.remote.service

import com.alexzh.imbarista.remote.model.*
import io.reactivex.Single
import retrofit2.http.*

interface CoffeeDrinksService {

    @Headers("Content-Type: application/json")
    @POST("users")
    fun createUser(
        @Body user: UserModel
    ): Single<ResponseModel<UserModel>>

    @GET("users")
    fun getCurrentUser(
        @Header("Authorization") token: String
    ): Single<ResponseModel<UserModel>>

    @Headers("Content-Type: application/json")
    @POST("sessions")
    fun logIn(
        @Body user: UserModel
    ): Single<ResponseModel<SessionModel>>

    @Headers("Content-Type: application/json")
    @PATCH("sessions/{sessionId}")
    fun refreshToken(
        @Path("sessionId") sessionId: Long,
        @Header("Authorization") token: String,
        @Body refreshToken: RefreshTokenModel
    ): Single<ResponseModel<SessionModel>>

    @Headers("Content-Type: application/json")
    @DELETE("sessions/{sessionId}")
    fun logOut(
        @Path("sessionId") sessionId: Long,
        @Header("Authorization") token: String
    ): Single<ResponseModel<SessionModel>>

    @GET("coffee-drinks")
    fun getCoffeeDrinks(
        @Header("Authorization") token: String
    ): Single<ResponseModel<CoffeeDrinkDataModel>>

    @Headers("Content-Type: application/json")
    @PATCH("coffee-drinks/{coffeeId}")
    fun markDrinkAsFavourite(
        @Path("coffeeId") coffeeId: Long,
        @Header("Authorization") token: String,
        @Body favourite: CoffeeDrinkFavouriteValueModel
    ): Single<ResponseModel<CoffeeDrinkDataModel>>

    @GET("coffee-drinks/page/{pageNumber}")
    fun getCoffeeDrinksByPageNumber(
        @Header("Authorization") token: String,
        @Path("pageNumber") pageNumber: Long
    ): Single<ResponseModel<PagingCoffeeDataModel>>
}