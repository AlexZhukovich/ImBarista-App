package com.alexzh.imbarista.di

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.alexzh.data.CoffeeDrinksDataRepository
import com.alexzh.data.MapDataProvider
import com.alexzh.data.NearMeCafeDataRepository
import com.alexzh.data.UserDataRepository
import com.alexzh.data.mapper.*
import com.alexzh.data.repository.*
import com.alexzh.data.store.*
import com.alexzh.imbarista.cache.CoffeeDrinksCacheRepositoryImpl
import com.alexzh.imbarista.cache.SharedPreferencesRepository
import com.alexzh.imbarista.cache.mapper.MapCacheMapper
import com.alexzh.imbarista.cache.mapper.SessionCacheMapper
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
import com.alexzh.imbarista.remote.mapper.SessionRemoteMapper
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
    viewModel { CoffeeDrinkDetailsViewModel(getCoffeeDrinkById = get(), addCoffeeDrinkToFavourites = get(), removeCoffeeDrinkFromFavourite = get(), coffeeDrinkViewMapper = get(), authViewExceptionMapper = get()) }
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
    factory<MapProvider> { MapDataProvider(preferencesRepository = get(), mapDataMapper = get()) }
    factory { MapFactory(mapProvider = get(), mapViewMapper = get()) }
}

val mapperModule = module {
    factory { CoffeeDrinkDataMapper() }
    factory { CoffeeDrinkRemoteMapper() }
    factory { CoffeeDrinkViewMapper() }

    factory { UserRemoteMapper() }
    factory { UserDataMapper() }
    factory { UserViewMapper() }

    factory { SessionRemoteMapper() }
    factory { SessionCacheMapper() }
    factory { SessionDataMapper() }
    factory { SessionViewMapper() }

    factory { MapCacheMapper() }
    factory { MapDataMapper() }
    factory { MapViewMapper() }

    factory { CafeRemoteMapper() }
    factory { CafeDataMapper() }
    factory { CafeViewMapper() }

    factory { AuthViewExceptionMapper() }
    factory { AuthExceptionMapper() }
    factory { AuthExceptionRemoteMapper() }

    factory { HttpExceptionMapper() }

    factory { UserAlreadyExistExceptionRemoteMapper() }
    factory { UserAlreadyExistExceptionDataMapper() }
    factory { UserAlreadyExistExceptionViewMapper() }
}

val dataModule = module {
    // android
    factory<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(androidContext()) }

    // domain
    factory<CoffeeDrinksRepository> { CoffeeDrinksDataRepository(coffeeDrinkDataMapper = get(), cacheRepository = get(), storeFactory = get(), userRepository = get(), preferencesRepository = get()) }
    single<UserRepository>(override = true) { UserDataRepository(userDataStore = get(), userDataMapper = get(), sessionDataMapper = get(), preferencesRepository = get()) }
    factory<NearMeCafeRepository> { NearMeCafeDataRepository(cafeDataStore = get(), cafeDataMapper = get()) }

    // remote
    factory { CoffeeDrinksServiceFactory().createCoffeeDrinksService(true) }
    factory<TomTomSearchService> { TomTomDataSearchService(androidContext() as Application) }

    // data
    factory<CafeRemoteRepository> { CafeRemoteRepositoryImpl(tomTomSearchService = get(), cafeRemoteMapper = get()) }
    factory<CoffeeDrinksRemoteRepository> { CoffeeDrinkRemoteRepositoryImpl(service = get(), coffeeDrinkRemoteMapper = get(), httpExceptionMapper = get(), authExceptionMapper = get()) }
    factory<CoffeeDrinksCacheRepository> { CoffeeDrinksCacheRepositoryImpl() }
    factory { CoffeeDrinksRemoteDataStore(remoteRepository = get(), preferencesRepository = get(), authExceptionMapper = get()) }
    factory { CoffeeDrinksCacheDataStore(cacheRepository = get()) }
    factory { CoffeeDrinksDataStoreFactory(remoteDataStore = get(), cacheDataStore = get()) }
    factory<UserRemoteRepository> { UserRemoteRepositoryImpl(service = get(), userRemoteMapper = get(), sessionRemoteMapper = get(), userAlreadyExceptionExceptionRemoteMapper = get(), authExceptionMapper = get()) }
    factory<UserDataStore> { UserRemoteDataStore(repository = get(), preferencesRepository = get(), userAlreadyExistExceptionDataMapper = get(), authExceptionMapper = get()) }
    factory<PreferencesRepository> { SharedPreferencesRepository(prefs = get(), sessionCacheMapper = get(), mapCacheMapper = get()) }
    factory<CafeDataStore> { CafeRemoteDataStore(repository = get(), preferencesRepository = get()) }
}

val executorModule = module {
    factory<PostExecutionThread> { UiThread() }
}