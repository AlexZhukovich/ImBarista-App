package com.alexzh.imbarista.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.alexzh.data.CoffeeDrinksDataRepository
import com.alexzh.data.MapDataProvider
import com.alexzh.data.NearMeCafeDataRepository
import com.alexzh.data.UserDataRepository
import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.mapper.UserAlreadyExistExceptionMapper
import com.alexzh.data.repository.*
import com.alexzh.data.store.*
import com.alexzh.imbarista.cache.CoffeeDrinksCacheRepositoryImpl
import com.alexzh.imbarista.cache.SharedPreferencesRepository
import com.alexzh.imbarista.cache.mapper.MapMapper
import com.alexzh.imbarista.domain.MapProvider
import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.cafe.GetNearCafeFromTomTomSource
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinkById
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinks
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.AddCoffeeDrinkToFavourites
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.RemoveCoffeeDrinkFromFavourite
import com.alexzh.imbarista.domain.interactor.user.*
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.imbarista.domain.repository.NearMeCafeRepository
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.imbarista.executor.UiThread
import com.alexzh.imbarista.mapper.*
import com.alexzh.imbarista.remote.CafeRemoteRepositoryImpl
import com.alexzh.imbarista.remote.CoffeeDrinkRemoteRepositoryImpl
import com.alexzh.imbarista.remote.UserRemoteRepositoryImpl
import com.alexzh.imbarista.remote.mapper.*
import com.alexzh.imbarista.remote.service.CoffeeDrinksServiceFactory
import com.alexzh.imbarista.remote.service.TomTomDataSearchService
import com.alexzh.imbarista.remote.service.TomTomSearchService
import com.alexzh.imbarista.ui.map.MapFactory
import com.alexzh.imbarista.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LogInViewModel(logIn = get(), mapper = get()) }
    viewModel { CreateAccountViewModel(createAccount = get(), mapper = get()) }
    viewModel { CheckExistingSessionViewModel(getExistingSession = get(), mapper = get()) }
    viewModel { LogOutViewModel(logOut = get(), authViewExceptionMapper = get()) }
    viewModel { GetCurrentUserViewModel(getCurrentUser = get(), mapper = get()) }
    viewModel { GetCoffeeDrinksViewModel(getCoffeeDrinks = get(), addCoffeeDrinkToFavourites = get(), removeCoffeeDrinkFromFavourite = get(), coffeeDrinkViewMapper = get(), authViewExceptionMapper = get()) }
    viewModel { CoffeeDrinkDetailsViewModel(getCoffeeDrinkById = get(), addCoffeeDrinkToFavourites = get(), removeCoffeeDrinkFromFavourite = get(), coffeeDrinkViewMapper = get()) }
    viewModel { TomTomMapViewModel(getNearCafeFromTomTomSource = get(), cafeViewMapper = get(), application = androidContext() as Application) }
}

val useCaseModule = module {
    factory { LogIn(userRepository = get(), postExecutionThread = get()) }
    factory { LogOut(userRepository = get(), postExecutionThread = get()) }
    factory { CreateAccount(userRepository = get(), postExecutionThread = get()) }
    factory { GetExistingSession(userRepository = get(), postExecutionThread = get()) }
    factory { GetCurrentUser(userRepository = get(), postExecutionThread = get()) }
    factory { GetCoffeeDrinks(coffeeDrinksRepository = get(), postExecutionThread = get()) }
    factory { GetCoffeeDrinkById(coffeeDrinksRepository = get(), postExecutionThread = get()) }
    factory { AddCoffeeDrinkToFavourites(coffeeDrinksRepository = get(), postExecutionThread = get()) }
    factory { RemoveCoffeeDrinkFromFavourite(coffeeDrinksRepository = get(), postExecutionThread = get()) }
    factory { GetNearCafeFromTomTomSource(nearMeCafeRepository = get(), postExecutionThread = get()) }
}

val mapProviderModule = module {
    factory<MapProvider> { MapDataProvider(preferencesRepository = get(), mapMapper = get()) }
    factory { MapFactory(mapProvider = get(), mapViewMapper = get()) }
}

val mapperModule = module {
    factory { CoffeeMapper() }
    factory { com.alexzh.imbarista.remote.mapper.CoffeeMapper() }
    factory { SessionViewMapper() }
    factory { UserViewMapper() }
    factory { UserMapper() }
    factory { com.alexzh.data.mapper.UserMapper() }
    factory { SessionMapper() }
    factory { com.alexzh.data.mapper.SessionMapper() }
    factory { com.alexzh.imbarista.cache.mapper.SessionMapper() }
    factory { CoffeeDrinkViewMapper() }
    factory { HttpExceptionMapper() }
    factory { MapMapper() }
    factory { com.alexzh.data.mapper.MapMapper() }
    factory { MapViewMapper() }
    factory { CafeMapper() }
    factory { com.alexzh.data.mapper.CafeMapper() }
    factory { CafeViewMapper() }
    factory { UserAlreadyExistViewExceptionMapper() }
    factory { UserAlreadyExistExceptionMapper() }
    factory { com.alexzh.imbarista.remote.mapper.UserAlreadyExistExceptionMapper() }
    factory { AuthViewExceptionMapper() }
    factory { AuthExceptionMapper() }
    factory { com.alexzh.data.mapper.AuthExceptionMapper() }
}

val dataModule = module {
    factory { CoffeeDrinksServiceFactory().createCoffeeDrinksService(true) }
    factory<CoffeeDrinksCacheRepository> { CoffeeDrinksCacheRepositoryImpl() }
    factory<CoffeeDrinksRemoteRepository> { CoffeeDrinkRemoteRepositoryImpl(service = get(), coffeeMapper = get(), httpExceptionMapper = get(), authExceptionMapper = get()) }
    factory { CoffeeDrinksCacheDataStore(cacheRepository = get()) }
    factory { CoffeeDrinksRemoteDataStore(remoteRepository = get(), preferencesRepository = get(), authExceptionMapper = get()) }
    factory { CoffeeDrinksDataStoreFactory(remoteDataStore = get(), cacheDataStore = get()) }
    factory<CoffeeDrinksRepository> { CoffeeDrinksDataRepository(coffeeMapper = get(), cacheRepository = get(), storeFactory = get(), userRepository = get(), preferencesRepository = get()) }
    factory<UserRemoteRepository> { UserRemoteRepositoryImpl(service = get(), userMapper = get(), sessionMapper = get(), userAlreadyExceptionExceptionMapper = get(), authExceptionMapper = get()) }
    factory<UserDataStore> { UserRemoteDataStore(repository = get(), preferencesRepository = get(), userAlreadyExistExceptionMapper = get(), authExceptionMapper = get()) }
    factory<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    factory<PreferencesRepository> { SharedPreferencesRepository(prefs = get(), sessionMapper = get(), mapMapper = get()) }
    single<UserRepository>(override = true) { UserDataRepository(userDataStore = get(), userMapper = get(), sessionMapper = get(), preferencesRepository = get()) }
    factory<TomTomSearchService> { TomTomDataSearchService(androidContext() as Application) }
    factory<CafeRemoteRepository> { CafeRemoteRepositoryImpl(tomTomSearchService = get(), cafeMapper = get()) }
    factory<CafeDataStore> { CafeRemoteDataStore(repository = get(), preferencesRepository = get()) }
    factory<NearMeCafeRepository> { NearMeCafeDataRepository(cafeDataStore = get(), cafeMapper = get()) }
}

val executorModule = module {
    factory<PostExecutionThread> { UiThread() }
}