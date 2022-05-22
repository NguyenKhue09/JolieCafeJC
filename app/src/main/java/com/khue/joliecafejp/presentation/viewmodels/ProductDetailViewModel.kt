package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants.Companion.IS_FAV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    val userToken = dataStoreUseCases.readUserTokenUseCase()

    private val _isFav = mutableStateOf(false)
    val isFav: State<Boolean> = _isFav

    init {
        setFavProductState(isFav = savedStateHandle.get<Boolean>(IS_FAV) ?: false)
    }

    private val _getProductDetailResponse =
        MutableStateFlow<ApiResult<Product>>(ApiResult.Loading())
    val getProductDetailResponse: StateFlow<ApiResult<Product>> = _getProductDetailResponse

    private val _addUserFavResponse =
        MutableStateFlow<ApiResult<Unit>>(ApiResult.Loading())
    val addUserFavResponse: StateFlow<ApiResult<Unit>> = _addUserFavResponse

    private val _removeUserFavResponse =
        MutableStateFlow<ApiResult<Unit>>(ApiResult.Loading())
    val removeUserFavResponse: StateFlow<ApiResult<Unit>> = _removeUserFavResponse

    fun getProductDetail(token: String, productId: String) {
        viewModelScope.launch {
            _getProductDetailResponse.value = ApiResult.Loading()
            try {
                val response =
                    apiUseCases.getProductDetailUseCase(token = token, productId = productId)
                _getProductDetailResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _getProductDetailResponse.value = ApiResult.Error(e.message)
            }
        }
    }

    fun addUserFavProduct(token: String, productId: String) {
        viewModelScope.launch {
            _addUserFavResponse.value = ApiResult.Loading()
            try {
                val response =
                    apiUseCases.addUserFavoriteProductUseCase(token = token, productId = productId)
                _addUserFavResponse.value = handleNullDataApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _addUserFavResponse.value = ApiResult.Error(e.message)
            }
        }
    }

    fun removeUserFavProduct(token: String, productId: String) {
        viewModelScope.launch {
            _removeUserFavResponse.value = ApiResult.Loading()
            try {
                val response =
                    apiUseCases.removeUserFavProductByProductId(token = token, productId = productId)
                _removeUserFavResponse.value = handleNullDataApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _removeUserFavResponse.value = ApiResult.Error(e.message)
            }
        }
    }

    fun setFavProductState(isFav: Boolean) {
        _isFav.value = isFav
    }

    private fun <T> handleApiResponse(response: Response<ApiResponseSingleData<T>>): ApiResult<T> {
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

    private fun <T> handleNullDataApiResponse(response: Response<ApiResponseSingleData<T>>): ApiResult<T> {
        return when {
            response.message().toString().contains("timeout") -> {
                ApiResult.Error("Timeout")
            }
            response.code() == 500 -> {
                ApiResult.Error(response.message())
            }
            response.isSuccessful -> {
                ApiResult.NullDataSuccess()
            }
            else -> {
                ApiResult.Error(response.message())
            }
        }
    }
}