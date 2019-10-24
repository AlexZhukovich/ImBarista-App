package com.alexzh.imbarista.di

import com.alexzh.imbarista.domain.repository.UserRepository
import io.mockk.mockk
import org.koin.dsl.module

val testDataModule = module {
    single<UserRepository>(override = true) { mockk() }
}