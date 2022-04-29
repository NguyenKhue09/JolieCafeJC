package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseSingleData<T>(
    val success: Boolean,
    val message: String,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val data: T?
)
