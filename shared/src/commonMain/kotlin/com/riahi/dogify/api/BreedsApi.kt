package com.riahi.dogify.api

import com.riahi.dogify.api.model.BreedImageResponse
import com.riahi.dogify.api.model.BreedsResponse
import io.ktor.client.call.*
import io.ktor.client.request.*
import kotlin.collections.get

internal class BreedsApi : KtorApi() {

    suspend fun getBreeds(): BreedsResponse = client.get {
            apiUrl("breeds/list")
        }.body()

    suspend fun getRandomBreedImageFor(breed: String): BreedImageResponse = client.get {
        apiUrl("breed/$breed/images/random")
    }.body()
}