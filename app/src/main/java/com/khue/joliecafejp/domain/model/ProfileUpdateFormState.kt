package com.khue.joliecafejp.domain.model

data class ProfileUpdateFormState(
    val password: String = "",
    val passwordError: String = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String = "",
    val newPassword: String = "",
    val newPasswordError: String = "",
    val userName: String = "",
    val userNameError: String = "",
    val userPhoneNumber: String = "",
    val userPhoneNumberError: String = ""
)
