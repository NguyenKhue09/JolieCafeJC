package com.khue.joliecafejp.domain.model

data class ProfileUpdateFormState(
    val password: String = "",
    val passwordError: String = "",
    val currentPassword: String = "password",
    val currentPasswordError: String = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String = "",
    val newPassword: String = "",
    val newPasswordError: String = "",
    val userName: String = "",
    val userNameError: String = "",
    val userPhoneNumber: String = "",
    val userPhoneNumberError: String = ""
)
