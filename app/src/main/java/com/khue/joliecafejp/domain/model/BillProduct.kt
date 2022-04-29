package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BillProduct(
    val product: Product,
    val size: String,
    val quantity: Int,
    val price: Double
)
