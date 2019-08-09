package com.alexzh.imbarista.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.alexzh.data.CoffeeDrinksDataRepository
import com.alexzh.data.UserDataRepository
import com.alexzh.data.mapper.CoffeeMapper
import com.alexzh.data.mapper.IngredientMapper
import com.alexzh.data.repository.CoffeeDrinksCacheRepository
import com.alexzh.data.repository.CoffeeDrinksRemoteRepository
import com.alexzh.data.repository.PreferencesRepository
import com.alexzh.data.repository.UserRemoteRepository
import com.alexzh.data.store.*
import com.alexzh.imbarista.cache.CoffeeDrinksCacheRepositoryImpl
import com.alexzh.imbarista.cache.SharedPreferencesRepository
import com.alexzh.imbarista.domain.executor.PostExecutionThread
import com.alexzh.imbarista.domain.interactor.coffeedrink.browse.GetCoffeeDrinks
import com.alexzh.imbarista.domain.interactor.user.*
import com.alexzh.imbarista.domain.repository.CoffeeDrinksRepository
import com.alexzh.imbarista.domain.repository.UserRepository
import com.alexzh.imbarista.executor.UiThread
import com.alexzh.imbarista.mapper.SessionViewMapper
import com.alexzh.imbarista.mapper.UserViewMapper
import com.alexzh.imbarista.remote.CoffeeDrinkRemoteRepositoryImpl
import com.alexzh.imbarista.remote.UserRemoteRepositoryImpl
import com.alexzh.imbarista.remote.mapper.IngredientsMapper
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
    viewModel { CurrentUserViewModel(getCurrentUser = get(), mapper = get()) }
}

val useCaseModule = module {
    factory { LogIn(userRepository = get(), postExecutionThread = get()) }
    factory { LogOut(userRepository = get(), postExecutionThread = get()) }
    factory { CreateAccount(userRepository = get(), postExecutionThread = get()) }
    factory { GetExistingSession(userRepository = get(), postExecutionThread = get()) }
    factory { GetCurrentUser(userRepository = get(), postExecutionThread = get()) }
    factory { GetCoffeeDrinks(coffeeDrinksRepository = get(), postExecutionThread = get()) }
}

val mapperModule = module {
    factory { IngredientMapper() }
    factory { CoffeeMapper(ingredientMapper = get()) }
    factory { IngredientsMapper() }
    factory { com.alexzh.imbarista.remote.mapper.CoffeeMapper(ingredientMapper = get()) }
    factory { SessionViewMapper() }
    factory { UserViewMapper() }
    factory { UserMapper() }
    factory { com.alexzh.data.mapper.UserMapper() }
    factory { SessionMapper() }
    factory { com.alexzh.data.mapper.SessionMapper() }
    factory { com.alexzh.imbarista.cache.mapper.SessionMapper() }
}

val dataModule = module {
    factory { CoffeeDrinksServiceFactory().createCoffeeDrinksService(true) }
    factory<CoffeeDrinksCacheRepository> { CoffeeDrinksCacheRepositoryImpl() }
    factory<CoffeeDrinksRemoteRepository> { CoffeeDrinkRemoteRepositoryImpl(service = get(), mapper = get()) }
    factory { CoffeeDrinksCacheDataStore(cacheRepository = get()) }
    factory { CoffeeDrinksRemoteDataStore(remoteRepository = get()) }
    factory { CoffeeDrinksDataStoreFactory(remoteDataStore = get(), cacheDataStore = get()) }
    factory<CoffeeDrinksRepository> { CoffeeDrinksDataRepository(mapper = get(), cacheRepository = get(), storeFactory = get()) }
    factory<UserRemoteRepository> { UserRemoteRepositoryImpl(service = get(), userMapper = get(), sessionMapper = get()) }
    factory<UserDataStore> { UserRemoteDataStore(repository = get()) }
    factory<SharedPreferences> { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
    factory<PreferencesRepository> { SharedPreferencesRepository(prefs = get(), sessionMapper = get()) }
    factory<UserRepository> { UserDataRepository(userDataStore = get(), userMapper = get(), sessionMapper = get(), preferencesRepository = get()) }
}

val executorModule = module {
    factory<PostExecutionThread> { UiThread() }
}