package com.riahi.dogify.api.model

import kotlinx.serialization.SerialName

@kotlinx.serialization.Serializable
internal data class BreedsResponse (
    @SerialName("message") val breeds: List<String>
)