package com.khue.joliecafejp.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class User(
    @SerialName("_id")
    val id: String,
    @SerialName("fullname")
    val fullName: String,
    val email: String,
    val phone: String? = null,
    val dob: String ? = null,
    val thumbnail: String ?= null,
    val token: String,
    val defaultAddress: String? = null,
    val coins: Int,
    val scores: Int,
    val disable: Boolean,
    @SerialName("updatedAt")
    val updatedAt: String? = null,
    @SerialName("createdAt")
    val createdAt: String? = null
)
