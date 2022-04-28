package com.khue.joliecafejp.domain.model

data class RegistrationFormState(
    val email: String = "",
    val emailError: String = "",
    val password: String = "",
    val passwordError: String = "",
    val confirmPassword: String = "",
    val confirmPasswordError: String = "",
    val userName: String = "",
    val userNameError: String = ""
)
