package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.RegistrationFormState
import com.khue.joliecafejp.domain.model.User
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.domain.use_cases.ValidationUseCases
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.RegistrationFormEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val validationUseCases: ValidationUseCases,
    private val apiUseCases: ApiUseCases,
    private val dataStoreUseCases: DataStoreUseCases
): ViewModel() {

//    private val _user: MutableStateFlow<User?> = MutableStateFlow(null)
//    val user: StateFlow<User?> = _user

    private val _userLoginResponse = MutableStateFlow<ApiResult<User>>(ApiResult.Loading())
    val userLoginResponse: StateFlow<ApiResult<User>> = _userLoginResponse

    val userToken  = dataStoreUseCases.readUserTokenUseCase()

    var state =  MutableStateFlow(RegistrationFormState())

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    fun userLogin(userId: String) =
        viewModelScope.launch {
            _userLoginResponse.value = ApiResult.Loading()
            try {
                val response = apiUseCases.userLoginUseCase(userId = userId)
                _userLoginResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _userLoginResponse.value = ApiResult.Error(e.message)
            }
        }

    fun createUser(userData: Map<String, String>) =
        viewModelScope.launch {
            _userLoginResponse.value = ApiResult.Loading()
            try {
                val response = apiUseCases.createUserUseCase(data = userData)
                _userLoginResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _userLoginResponse.value = ApiResult.Error(e.message)
            }

        }

    fun getUserInfos(token: String) =
        viewModelScope.launch {
            _userLoginResponse.value = ApiResult.Loading()
            try {
                val response = apiUseCases.getUserInfosUseCase(token = token)
                _userLoginResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _userLoginResponse.value = ApiResult.Error(e.message)
            }

        }

    private fun handleApiResponse(response: Response<ApiResponseSingleData<User>>): ApiResult<User> {
        val result = response.body()
        return when {
            response.message().toString().contains("timeout") -> {
                ApiResult.Error("Timeout")
            }
            response.code() == 500 -> {
                ApiResult.Error(response.message())
            }
            response.isSuccessful -> {
                saveUserToken(result!!.data!!.token)
                ApiResult.Success(result.data!!)
            }
            else -> {
                ApiResult.Error(response.message())
            }
        }
    }

    fun signOut() {
        saveUserToken("")
    }

    private fun saveUserToken(userToken: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreUseCases.saveUserTokenUseCase(userToken = userToken)
        }

    fun onEvent(event: RegistrationFormEvent) {
        when(event) {
            is RegistrationFormEvent.EmailChanged -> {
                state.value = state.value.copy(email = event.email)
            }
            is RegistrationFormEvent.PasswordChanged -> {
                state.value = state.value.copy(password = event.password)
            }
            is RegistrationFormEvent.Submit -> {
                submitData()
            }
            else -> {}
        }
    }

    private fun submitData() {
        val emailResult = validationUseCases.validationEmailUseCaseUseCase.execute(state.value.email)
        val passwordResult = validationUseCases.validationPasswordUseCase.execute(state.value.password)

        val hasError = listOf(
            emailResult,
            passwordResult,
        ).any { !it.successful }

        if (hasError) {
            state.value = state.value.copy(
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
