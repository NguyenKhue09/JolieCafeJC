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
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val validationUseCases: ValidationUseCases
): ViewModel() {

    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.ConfirmPasswordChanged -> {
                state = state.copy(confirmPassword =  event.confirmPassword)
            }
            is RegistrationFormEvent.UserNameChanged -> {
                state = state.copy(userName = event.username)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
        }
    }

    private fun submitData() {
        val emailResult = validationUseCases.validationEmailUseCaseUseCase.execute(state.email)
        val passwordResult = validationUseCases.validationPasswordUseCase.execute(state.password)
        val confirmPasswordResult = validationUseCases.validationConfirmPasswordUseCase.execute(state.password, state.confirmPassword)
        val userNameResult = validationUseCases.validationUserNameUseCaseUseCase.execute(state.userName)

        val hasError = listOf(
            emailResult,
            passwordResult,
            confirmPasswordResult,
            userNameResult
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
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