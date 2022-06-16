package com.khue.joliecafejp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartItem(
    @SerialName("_id")
    val id: String,
    val productId: String,
    @SerialName("product_detail")
    val productDetail:Product,
    val size: String,
    var quantity: Int,
    var price: Double
)

