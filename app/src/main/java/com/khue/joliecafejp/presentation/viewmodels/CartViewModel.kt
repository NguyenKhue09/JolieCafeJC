package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khue.joliecafejp.domain.model.*
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.domain.use_cases.ValidationUseCases
import com.khue.joliecafejp.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CartViewModel@Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    val userToken  = dataStoreUseCases.readUserTokenUseCase()

    private val _getCartItemsResponse = MutableStateFlow<ApiResult<List<CartItemByCategory>>>(ApiResult.Idle())
    val getCartItemResponse: StateFlow<ApiResult<List<CartItemByCategory>>> = _getCartItemsResponse

    fun getCartItems(token: String) = viewModelScope.launch {
        try {
            _getCartItemsResponse.value = ApiResult.Loading()
            val response = apiUseCases.getCartItemsUseCase(token = token)
            _getCartItemsResponse.value = handleApiResponse(response = response)
        } catch (e: Exception) {
            e.printStackTrace()
            _getCartItemsResponse.value = ApiResult.Error(e.message)
        }
    }

    private fun handleApiResponse(response: Response<ApiResponseMultiData<CartItemByCategory>>): ApiResult<List<CartItemByCategory>> {
        val result = response.body()
        return when {
            response.message().toString().contains("timeout") -> {
                ApiResult.Error("Timeout")
            }
            response.code() == 500 -> {
                ApiResult.Error(response.message())
            }
            response.isSuccessful -> {
                ApiResult.Success(result?.data!!)
            }
            else -> {
                ApiResult.Error(response.message())
            }
        }
    }
}