package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khue.joliecafejp.domain.model.ProfileUpdateFormState
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
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
    private val apiUseCases: ApiUseCases,
    private val validationUseCases: ValidationUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel()  {

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
            else -> {
            }
        }
    }

    private fun phoneAndNameSubmit() {
        val nameResult = validationUseCases.validationUserNameUseCaseUseCase.execute(state.value.userName)
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
            state.value = state.value.copy(
                userNameError = "",
                userPhoneNumberError = "",
            )
        }
        viewModelScope.launch {
            validationEventChannel.send(ValidationEvent.PhoneAndNameSuccess)
        }
    }

    sealed class ValidationEvent {
        object PhoneAndNameSuccess: ValidationEvent()
    }
}