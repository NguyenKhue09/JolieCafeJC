package com.khue.joliecafejp.presentation.viewmodels


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khue.joliecafejp.domain.model.RegistrationFormState
import com.khue.joliecafejp.domain.use_cases.ValidationUseCases
import com.khue.joliecafejp.utils.RegistrationFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validationUseCases: ValidationUseCases
): ViewModel() {

    var state =  MutableStateFlow(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state.value = state.value.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state.value = state.value.copy(password = event.password)
            }
            is RegistrationFormEvent.ConfirmPasswordChanged -> {
                state.value = state.value.copy(confirmPassword =  event.confirmPassword)
            }
            is RegistrationFormEvent.UserNameChanged -> {
                state.value = state.value.copy(userName = event.username)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validationUseCases.validationEmailUseCaseUseCase.execute(state.value.email)
        val passwordResult = validationUseCases.validationPasswordUseCase.execute(state.value.password)
        val confirmPasswordResult = validationUseCases.validationConfirmPasswordUseCase.execute(state.value.password, state.value.confirmPassword)
        val userNameResult = validationUseCases.validationUserNameUseCaseUseCase.execute(state.value.userName)

        val hasError = listOf(
            emailResult,
            passwordResult,
            confirmPasswordResult,
            userNameResult
        ).any { !it.successful }

        if (hasError) {
            state.value = state.value.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                confirmPasswordError = confirmPasswordResult.errorMessage,
                userNameError = userNameResult.errorMessage
            )
            return
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.Success)
        }
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}