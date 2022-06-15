package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class CartItemByCategory(
    val type: String,
    val products: List<CartItem>
)