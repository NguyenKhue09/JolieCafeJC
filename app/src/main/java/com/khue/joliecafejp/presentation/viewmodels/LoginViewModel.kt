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
class LoginViewModel @Inject constructor(
    private val validationUseCases: ValidationUseCases
): ViewModel() {

//    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
//    val user: StateFlow<User?> = _user

    var state by mutableStateOf(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

//    init {
//        if (FirebaseAuth.getInstance().currentUser != null) {
//            _user.value = User(
//                email = FirebaseAuth.getInstance().currentUser?.email!!,
//                displayName = FirebaseAuth.getInstance().currentUser?.displayName!!
//            )
//        }
//    }

//    fun signIn(email: String, displayName: String){
//        _user.value = User(email, displayName)
//        println(user.value)
//    }
//
//    fun signOut() {
//        _user.value = null
//    }

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state = state.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
            else -> {}
        }
    }

    private fun submitData() {
        val emailResult = validationUseCases.validationEmailUseCaseUseCase.execute(state.email)
        val passwordResult = validationUseCases.validationPasswordUseCase.execute(state.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
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