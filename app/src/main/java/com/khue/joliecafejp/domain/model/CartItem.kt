package com.khue.joliecafejp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    @SerialName("productId")
    val product: Product,
    val size: String,
    val quantity: Int,
    val price: Double
)
