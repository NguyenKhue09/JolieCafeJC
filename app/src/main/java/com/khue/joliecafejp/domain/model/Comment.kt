package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Comment(
    val userThumbnail: String,
    val userName: String,
    val content: String,
    val rating: Int,
    val createdAt: String?
)


