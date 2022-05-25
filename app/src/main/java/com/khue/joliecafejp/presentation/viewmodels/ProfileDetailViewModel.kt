package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.khue.joliecafejp.domain.model.ProfileUpdateFormState
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.domain.use_cases.ValidationUseCases
import com.khue.joliecafejp.utils.ProfileUpdateFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(
    private val validationUseCases: ValidationUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel()  {

    private val currentUser = FirebaseAuth.getInstance().currentUser

    val userToken  = dataStoreUseCases.readUserTokenUseCase()

    var state =  MutableStateFlow(ProfileUpdateFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onPhoneAndNameChangeEvent(event: ProfileUpdateFormEvent) {
        when(event) {
            is ProfileUpdateFormEvent.UserNameChanged -> {
                state.value = state.value.copy(userName = event.username)
            }
            is ProfileUpdateFormEvent.UserPhoneNumberChanged -> {
                state.value = state.value.copy(userPhoneNumber = event.userPhoneNumber)
            }
            is ProfileUpdateFormEvent.PhoneAndNameSubmit -> {
                phoneAndNameSubmit()
            }
            else -> {}
        }
    }

    fun onCurrentPasswordChangeEvent(event: ProfileUpdateFormEvent) {
        when(event) {
            is ProfileUpdateFormEvent.CurrentPasswordChanged -> {
                state.value = state.value.copy(currentPassword = event.currentPassword)
            }
            is ProfileUpdateFormEvent.CurrentPasswordSubmit -> {
                checkCurrentPassword()
            }
            else -> {}
        }
    }

    fun onCreateNewPasswordChangeEvent(event: ProfileUpdateFormEvent) {
        when(event) {
            is ProfileUpdateFormEvent.NewPasswordChanged -> {
                state.value = state.value.copy(newPassword = event.newPassword)
            }
            is ProfileUpdateFormEvent.ConfirmPasswordChanged -> {
                state.value = state.value.copy(confirmPassword = event.confirmPassword)
            }
            is ProfileUpdateFormEvent.ChangeNewPasswordSubmit -> {
                newPasswordSubmit()
            }
            else -> {}
        }
    }

    private fun checkCurrentPassword() {
        val currentPasswordResult = validationUseCases.validationPasswordUseCase.execute(state.value.currentPassword)

        val hasError = currentPasswordResult.successful

        if(!hasError) {
            state.value = state.value.copy(
                currentPasswordError = currentPasswordResult.errorMessage
            )
            return
        } else {
            state.value = state.value.copy(
                currentPasswordError = ""
            )
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.CurrentPasswordValid)
        }
    }

    private fun phoneAndNameSubmit() {
        val nameResult = validationUseCases.validationUserNameUseCase.execute(state.value.userName)
        val phoneResult = validationUseCases.validateUserPhoneNumberUseCase.execute(state.value.userPhoneNumber)

        val hasError = listOf(
            nameResult,
            phoneResult,
        ).any { !it.successful }

        if (hasError) {
            state.value = state.value.copy(
                userNameError = nameResult.errorMessage,
                userPhoneNumberError = phoneResult.errorMessage,
            )
            return
        } else {
            cleanNameAndPhoneError()
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.PhoneAndNameSuccess)
        }
    }

    private fun newPasswordSubmit() {
        val newPasswordResult = validationUseCases.validationPasswordUseCase.execute(state.value.newPassword)
        val confirmNewPasswordResult = validationUseCases.validationConfirmPasswordUseCase.execute(state.value.newPassword, state.value.confirmPassword)

        val hasError = listOf(
            newPasswordResult,
            confirmNewPasswordResult,
        ).any { !it.successful }

        if (hasError) {
            state.value = state.value.copy(
                newPasswordError = newPasswordResult.errorMessage,
                confirmPasswordError = confirmNewPasswordResult.errorMessage,
            )
            return
        } else {
            cleanChangeNewPasswordError()
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.NewPasswordValid)
        }
    }

    fun cleanChangeNewPasswordError() {
        state.value = state.value.copy(
            newPasswordError = "",
            confirmPasswordError = "",
        )
    }

    private fun cleanNameAndPhoneError() {
        state.value = state.value.copy(
            userNameError = "",
            userPhoneNumberError = "",
        )
    }

    fun reAuthentication(password: String) {
        currentUser?.let { firebaseUser ->
            val credential = EmailAuthProvider
                .getCredential(firebaseUser.email!!, password)

            firebaseUser.reauthenticate(credential).addOnSuccessListener {
                state.value = state.value.copy(
                    currentPasswordError = ""
                )
                viewModelScope.launch {
                    validationEventChannel.send(ValidationEvent.ReAuthenticationSuccess)
                }
            }.addOnFailureListener { err ->
                state.value = state.value.copy(
                    currentPasswordError = err.message!!
                )
            }
        }
    }

    fun updateNewPassword(password: String) {
        println(password)
        currentUser?.let { firebaseUser ->
            firebaseUser.updatePassword(password)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        validationEventChannel.send(ValidationEvent.ChangePasswordSuccess)
                    }
                }
                .addOnFailureListener {
                    viewModelScope.launch {
                        validationEventChannel.send(ValidationEvent.ChangePasswordFailed)
                    }
                }
        }
    }

    sealed class ValidationEvent {
        object PhoneAndNameSuccess: ValidationEvent()
        object CurrentPasswordValid: ValidationEvent()
        object ReAuthenticationSuccess: ValidationEvent()
        object NewPasswordValid: ValidationEvent()
        object ChangePasswordSuccess: ValidationEvent()
        object ChangePasswordFailed: ValidationEvent()
    }
}