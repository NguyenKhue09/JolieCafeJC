package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class ApiResponseMultiData<T>(
    val success: Boolean,
    val message: String,
    val prevPage: Int? = null,
    val nextPage: Int? = null,
    val data: List<T> = emptyList()
)
