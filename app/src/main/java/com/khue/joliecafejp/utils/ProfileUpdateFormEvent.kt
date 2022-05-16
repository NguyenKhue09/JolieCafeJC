package com.khue.joliecafejp.utils

sealed class ProfileUpdateFormEvent {
    data class PasswordChanged(val password: String): ProfileUpdateFormEvent()
    data class CurrentPasswordChanged(val currentPassword: String): ProfileUpdateFormEvent()
    data class ConfirmPasswordChanged(val confirmPassword: String): ProfileUpdateFormEvent()
    data class NewPasswordChanged(val newPassword: String): ProfileUpdateFormEvent()
    data class UserNameChanged(val username: String): ProfileUpdateFormEvent()
    data class UserPhoneNumberChanged(val userPhoneNumber: String): ProfileUpdateFormEvent()
    object PhoneAndNameSubmit: ProfileUpdateFormEvent()
    object CurrentPasswordSubmit: ProfileUpdateFormEvent()
    object ChangeNewPasswordSubmit: ProfileUpdateFormEvent()
}
