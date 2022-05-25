package com.khue.joliecafejp.domain.model

data class AddNewAddressFormState(
    val userName: String = "",
    val userNameError: String = "",
    val phone: String = "",
    val phoneError: String = "",
    val address: String = "",
    val addressError: String = "",
    val isDefault: Boolean = false
)
