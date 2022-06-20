package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val userId: User,
    val content: String,
    val rating: Int,
    val createdAt: String?,
    val updatedAt: String?,
)


