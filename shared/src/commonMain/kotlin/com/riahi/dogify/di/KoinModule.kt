package com.riahi.dogify.di

import com.riahi.dogify.api.BreedsApi
import com.riahi.dogify.database.createDriver
import com.riahi.dogify.db.DogifyDatabase
import com.riahi.dogify.usecase.FetchBreedsUseCase
import com.riahi.dogify.usecase.GetBreedsUseCase
import com.riahi.dogify.usecase.ToggleFavouriteStateUseCase
import com.riahi.dogify.util.getDispatcherProvider
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import repository.BreedsLocalSource
import repository.BreedsRemoteSource
import repository.BreedsRepository

private val utilityModule = module {
    factory { getDispatcherProvider() }
    single { DogifyDatabase(createDriver("dogify.db")) }
}

private val apiModule = module {
    factory { BreedsApi() }
}

private val repositoryModule = module {
    single { BreedsRepository(get(), get()) }
    factory { BreedsRemoteSource(get(), get()) }
    factory { BreedsLocalSource(get(), get()) }
}

private val usecaseModule = module {
    factory { GetBreedsUseCase() }
    factory { FetchBreedsUseCase() }
    factory { ToggleFavouriteStateUseCase() }
}

private val sharedModules = listOf(utilityModule, apiModule, repositoryModule, usecaseModule)

fun initKoin(appDeclaration: KoinAppDeclaration) = startKoin {
    appDeclaration()
    modules(sharedModules)
}