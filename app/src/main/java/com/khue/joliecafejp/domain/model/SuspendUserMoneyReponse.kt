package com.khue.joliecafejp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class SuspendUserMoneyResponse(
    val status: Int,
    var message: String,
    val amount: Long,
    val transid: String,
    val signature: String
)
