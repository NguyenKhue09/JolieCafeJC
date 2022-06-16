package com.khue.joliecafejp.domain.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Immutable
data class FavoriteProduct(
    @SerialName("_id")
    val id: String,
    val product: Product
)
