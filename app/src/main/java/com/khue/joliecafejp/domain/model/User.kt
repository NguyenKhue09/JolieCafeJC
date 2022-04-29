package com.khue.joliecafejp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val email: String,
    val displayName: String,
    @SerialName("fullname")
    val fullName: String,
    val phone: String,
    val dob: String,
    val thumbnail: String,
    val token: String,
    val defaultAddress: Address,
    val coins: Int,
    val scores: Int,
)
