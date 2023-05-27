package com.riahi.dogify.di

import com.riahi.dogify.api.BreedsApi
import com.riahi.dogify.usecase.FetchBreedsUseCase
import com.riahi.dogify.usecase.GetBreedsUseCase
import com.riahi.dogify.usecase.ToggleFavouriteStateUseCase
import com.riahi.dogify.util.getDispatcherProvider
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import repository.BreedsRemoteSource
import repository.BreedsRepository

private val utilityModule = module {
    factory { getDispatcherProvider() }
}

private val apiModule = module {
    factory { BreedsApi() }
}

private val repositoryModule = module {
    single { BreedsRepository(get()) }
    factory { BreedsRemoteSource(get(), get()) }
}

private val usecaseModule = module {
    factory { GetBreedsUseCase() }
    factory { FetchBreedsUseCase() }
    factory { ToggleFavouriteStateUseCase() }
}

private val sharedModules = listOf(utilityModule, apiModule, repositoryModule, usecaseModule)

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(sharedModules)
}