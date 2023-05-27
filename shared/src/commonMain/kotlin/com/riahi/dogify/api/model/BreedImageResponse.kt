package com.riahi.dogify.api.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
internal data class BreedImageResponse(
    @SerialName("message") val breedImageUrl: String
)
