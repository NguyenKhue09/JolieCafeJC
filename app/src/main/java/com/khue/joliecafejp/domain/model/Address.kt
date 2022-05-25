package com.khue.joliecafejp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    @SerialName("_id")
    val id: String,
    val userId: String,
    val userName: String,
    val phone: String,
    val address: String,
    val __v: Int
)
