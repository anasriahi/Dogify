package com.riahi.dogify.usecase

import com.riahi.dogify.model.Breed

class GetBreedsUseCase {
    suspend fun invoke() : List<Breed> = listOf(Breed("Test Get",""), )
}