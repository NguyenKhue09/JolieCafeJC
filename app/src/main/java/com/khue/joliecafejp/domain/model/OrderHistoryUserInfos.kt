package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class OrderHistoryUserInfos(
    val userId: String,
    val userName: String,
    val phone: String,
    val address: String
)
