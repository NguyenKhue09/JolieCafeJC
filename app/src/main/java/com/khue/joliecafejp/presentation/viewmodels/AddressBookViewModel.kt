package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.*
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.domain.use_cases.ValidationUseCases
import com.khue.joliecafejp.utils.AddNewAddressFormEvent
import com.khue.joliecafejp.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AddressBookViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases,
    private val validationUseCases: ValidationUseCases,
) : ViewModel() {

    val userToken = dataStoreUseCases.readUserTokenUseCase()

    private val _addressBooks = MutableStateFlow<PagingData<Address>>(PagingData.empty())
    val addressBooks: StateFlow<PagingData<Address>> = _addressBooks

    private var _addNewAddressResponse = MutableStateFlow<ApiResult<Address>>(ApiResult.Loading())
    val addNewAddressResponse: StateFlow<ApiResult<Address>> = _addNewAddressResponse
    private var _addNewDefaultAddressResponse = MutableStateFlow<ApiResult<User>>(ApiResult.Loading())
    val addNewDefaultAddressResponse: StateFlow<ApiResult<User>> = _addNewDefaultAddressResponse
    private var _deleteAddressResponse = MutableStateFlow<ApiResult<Address>>(ApiResult.Loading())
    val deleteAddressResponse: StateFlow<ApiResult<Address>> = _deleteAddressResponse
    private var _updateAddressResponse = MutableStateFlow<ApiResult<Address>>(ApiResult.Loading())
    val updateAddressResponse: StateFlow<ApiResult<Address>> = _updateAddressResponse

    private val validationEventChannel = Channel<ValidationEvent>()
    val validationEvents = validationEventChannel.receiveAsFlow()

    val addNewAddressFormState = MutableStateFlow(AddNewAddressFormState())

    fun getProducts(
        token: String
    ) {
        try {
            viewModelScope.launch {
                apiUseCases.getAddresses(token = token).cachedIn(viewModelScope).collectLatest {
                    _addressBooks.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _addressBooks.value = PagingData.empty()
        }
    }

    fun addNewAddress(data: Map<String, String>, token: String)  =
        viewModelScope.launch {
            _addNewAddressResponse.value = ApiResult.Loading()
            try {
                if (token.isEmpty()) Throwable("Unauthorized")
                val response = apiUseCases.addNewAddressUseCase(data = data, token = token)
                _addNewAddressResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _addNewAddressResponse.value = ApiResult.Error(e.message)
            }
        }

    fun deleteAddress(addressId: String, token: String)  =
        viewModelScope.launch {
            _deleteAddressResponse.value = ApiResult.Loading()
            try {
                if (token.isEmpty()) Throwable("Unauthorized")
                val response = apiUseCases.deleteAddressUseCase(addressId = addressId, token = token)
                _deleteAddressResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _deleteAddressResponse.value = ApiResult.Error(e.message)
            }
        }

    fun updateAddress(newAddressData: Map<String, String>, token: String)  =
        viewModelScope.launch {
            _updateAddressResponse.value = ApiResult.Loading()
            try {
                if (token.isEmpty()) Throwable("Unauthorized")
                val response = apiUseCases.updateAddressUseCase(newAddressData = newAddressData, token = token)
                _updateAddressResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _updateAddressResponse.value = ApiResult.Error(e.message)
            }
        }

    fun addNewDefaultAddress(data: Map<String, String>, token: String)  =
        viewModelScope.launch {
            _addNewDefaultAddressResponse.value = ApiResult.Loading()
            try {
                val response = apiUseCases.addNewDefaultAddressUseCase(data = data, token = token)
                _addNewDefaultAddressResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _addNewDefaultAddressResponse.value = ApiResult.Error(e.message)
            }
        }

    private fun <T> handleApiResponse(response: Response<ApiResponseSingleData<T>>): ApiResult<T> {
        return when {
            response.message().toString().contains("timeout") -> {
                ApiResult.Error("Timeout")
            }
            response.code() == 500 -> {
                ApiResult.Error(response.message())
            }
            response.isSuccessful -> {
                val result = response.body()
                if(result != null) {
                    ApiResult.Success(result.data!!)
                } else {
                    ApiResult.Error("Some things went wrong!")
                }
            }
            else -> {
                ApiResult.Error(response.message())
            }
        }
    }


    fun onEvent(event: AddNewAddressFormEvent) {
        when(event) {
            is AddNewAddressFormEvent.UserNameChanged -> {
                addNewAddressFormState.value = addNewAddressFormState.value.copy(userName = event.username)
            }
            is AddNewAddressFormEvent.PhoneChanged -> {
                addNewAddressFormState.value = addNewAddressFormState.value.copy(phone = event.phone)
            }
            is AddNewAddressFormEvent.AddressChanged -> {
                addNewAddressFormState.value = addNewAddressFormState.value.copy(address = event.address)
            }
            is AddNewAddressFormEvent.IsDefaultAddressChanged -> {
                addNewAddressFormState.value = addNewAddressFormState.value.copy(isDefault = event.isDefault)
            }
            is AddNewAddressFormEvent.Submit -> {
                submitNewAddressData()
            }
        }
    }

    private fun submitNewAddressData() {
        val userNameResult = validationUseCases.validationUserNameUseCase.execute(addNewAddressFormState.value.userName)
        val phoneResult = validationUseCases.validateUserPhoneNumberUseCase.execute(addNewAddressFormState.value.phone)
        val addressResult = validationUseCases.validateAddressUseCase.execute(addNewAddressFormState.value.address)

        val hasError = listOf(
            userNameResult,
            phoneResult,
            addressResult
        ).any { !it.successful }

        if (hasError) {
            addNewAddressFormState.value = addNewAddressFormState.value.copy(
                userNameError = userNameResult.errorMessage,
                phoneError = phoneResult.errorMessage,
                addressError = addressResult.errorMessage
            )
            return
        } else {
            clearErrorAddNewAddressFormState()
            viewModelScope.launch {
                validationEventChannel.send(ValidationEvent.Success)
            }
        }
    }

    fun clearErrorAddNewAddressFormState() {
        addNewAddressFormState.value = addNewAddressFormState.value.copy(
            userNameError = "",
            phoneError = "",
            addressError = ""
        )
    }

    fun clearDataAddNewAddressFormState() {
        addNewAddressFormState.value = addNewAddressFormState.value.copy(
            userName = "",
            phone = "",
            address = "",
            isDefault = false
        )
    }

    sealed class ValidationEvent {
        object Success: ValidationEvent()
    }
}