package com.alexzh.imbarista.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.alexzh.data.CoffeeDrinksDataRepository
import com.alexzh.data.UserDataRepository
import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.data.repository.UserRemoteRepository
import com.alexzh.data.store.*
import com.alexzh.imbarista.cache.CoffeeDrinksCacheRepositoryImpl
import com.alexzh.imbarista.cache.SharedPreferencesRepository
import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinkById
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinks
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.AddCoffeeDrinkToFavourites
import com.alexzh.imbarista.domain.interactor.coffeedrink.favourite.RemoveCoffeeDrinkFromFavourite
import com.alexzh.imbarista.domain.interactor.user.*
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.imbarista.executor.UiThread
import com.alexzh.imbarista.mapper.CoffeeDrinkViewMapper
import com.alexzh.imbarista.mapper.SessionViewMapper
import com.alexzh.imbarista.mapper.UserViewMapper
import com.alexzh.imbarista.remote.CoffeeDrinkRemoteRepositoryImpl
import com.alexzh.imbarista.remote.UserRemoteRepositoryImpl
import com.alexzh.imbarista.remote.mapper.HttpExceptionMapper
import com.alexzh.imbarista.remote.mapper.SessionMapper
import com.alexzh.imbarista.remote.mapper.UserMapper
import com.alexzh.imbarista.remote.service.CoffeeDrinksServiceFactory
import com.alexzh.imbarista.viewmodel.*
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { LogInViewModel(logIn = get(), mapper = get()) }
    viewModel { CreateAccountViewModel(createAccount = get(), mapper = get()) }
    viewModel { CheckExistingSessionViewModel(getExistingSession = get(), mapper = get()) }
    viewModel { LogOutViewModel(logOut = get()) }
    viewModel { GetCurrentUserViewModel(getCurrentUser = get(), mapper = get()) }
    viewModel { GetCoffeeDrinksViewModel(getCoffeeDrinks = get(), coffeeDrinkViewMapper = get()) }
    viewModel { CoffeeDrinkDetailsViewModel(getCoffeeDrinkById = get(), addCoffeeDrinkToFavourites = get(), removeCoffeeDrinkFromFavourite = get(), coffeeDrinkViewMapper = get()) }
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
}

val dataModule = module {
    factory { CoffeeDrinksServiceFactory().createCoffeeDrinksService(true) }
    factory<CoffeeDrinksCacheRepository> { CoffeeDrinksCacheRepositoryImpl() }
    factory<CoffeeDrinksRemoteRepository> { CoffeeDrinkRemoteRepositoryImpl(service = get(), coffeeMapper = get(), httpExceptionMapper = get()) }
    factory { CoffeeDrinksCacheDataStore(cacheRepository = get()) }
    factory { CoffeeDrinksRemoteDataStore(remoteRepository = get(), preferencesRepository = get()) }
    factory { CoffeeDrinksDataStoreFactory(remoteDataStore = get(), cacheDataStore = get()) }
    factory<CoffeeDrinksRepository> { CoffeeDrinksDataRepository(coffeeMapper = get(), cacheRepository = get(), storeFactory = get(), userRepository = get()) }
    factory<UserRemoteRepository> { UserRemoteRepositoryImpl(service = get(), userMapper = get(), sessionMapper = get()) }
    factory<UserDataStore> { UserRemoteDataStore(repository = get(), preferencesRepository = get()) }
    factory<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    factory<PreferencesRepository> { SharedPreferencesRepository(prefs = get(), sessionMapper = get()) }
    factory<UserRepository> { UserDataRepository(userDataStore = get(), userMapper = get(), sessionMapper = get(), preferencesRepository = get()) }
}

val executorModule = module {
    factory<PostExecutionThread> { UiThread() }
}