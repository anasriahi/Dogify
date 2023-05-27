package com.riahi.dogify.usecase

import com.riahi.dogify.model.Breed
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.BreedsRepository

class GetBreedsUseCase: KoinComponent {
    private val breedsRepository: BreedsRepository by inject()
    suspend fun invoke() : List<Breed> = breedsRepository.get()
}