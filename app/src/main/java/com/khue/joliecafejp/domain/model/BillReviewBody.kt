package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class BillReviewBody(
    val billId: String,
    val productIds: List<String>,
    val content: String,
    val rating: Float
)