package com.khue.joliecafejp.utils

sealed class RegistrationFormEvent {
    data class EmailChanged(val email: String): RegistrationFormEvent()
    data class PasswordChanged(val password: String): RegistrationFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String): RegistrationFormEvent()
    data class UserNameChanged(val username: String): RegistrationFormEvent()
    object Submit: RegistrationFormEvent()
}
