package com.riahi.dogify.usecase

import com.riahi.dogify.model.Breed

class FetchBreedsUseCase {
    suspend fun invoke() : List<Breed> = listOf(Breed("Test Fetch", ""), )
}