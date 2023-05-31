package com.riahi.dogify.usecase

import com.riahi.dogify.model.Breed
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import repository.BreedsRepository

class ToggleFavouriteStateUseCase : KoinComponent {
    private val breedsRepository: BreedsRepository by inject()

    suspend operator fun invoke(breed: Breed) {
        breedsRepository.update(breed.copy(isFavourite = !breed.isFavourite))
    }
}