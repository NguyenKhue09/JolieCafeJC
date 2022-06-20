package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.ApiResponseMultiData
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.Comment
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants.Companion.IS_FAV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
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
        MutableStateFlow<ApiResult<Product>>(ApiResult.Idle())
    val getProductDetailResponse: StateFlow<ApiResult<Product>> = _getProductDetailResponse

    private val _addUserFavResponse =
        MutableStateFlow<ApiResult<Unit>>(ApiResult.Idle())
    val addUserFavResponse: StateFlow<ApiResult<Unit>> = _addUserFavResponse

    private val _removeUserFavResponse =
        MutableStateFlow<ApiResult<Unit>>(ApiResult.Idle())
    val removeUserFavResponse: StateFlow<ApiResult<Unit>> = _removeUserFavResponse

    var getCommentProductResponse =
        MutableStateFlow<ApiResult<List<Comment>>>(ApiResult.Idle())
        private set

    var moreProducts = MutableStateFlow<PagingData<Product>>(PagingData.empty())
        private set

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
                    apiUseCases.removeUserFavProductByProductId(
                        token = token,
                        productId = productId
                    )
                _removeUserFavResponse.value = handleNullDataApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _removeUserFavResponse.value = ApiResult.Error(e.message)
            }
        }
    }

    fun getComment(token: String,productId: String) =
        viewModelScope.launch {
            getCommentProductResponse.value = ApiResult.Loading()
            try {
                val response = apiUseCases.getProductCommentUseCase(token,productId)
                getCommentProductResponse.value = handleCommentApiMultiResponse(response)
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                getCommentProductResponse.value = ApiResult.Error(e.message.toString())
            }
        }

    fun getProducts(
        productQuery: Map<String, String>,
        token: String
    ) {
        try {
            viewModelScope.launch {
                apiUseCases.getProductsUseCase(productQuery = productQuery, token = token).cachedIn(viewModelScope).collectLatest {
                    moreProducts.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            moreProducts.value = PagingData.empty()
        }
    }

    fun setFavProductState(isFav: Boolean) {
        _isFav.value = isFav
    }

    private fun handleCommentApiMultiResponse(response: Response<ApiResponseMultiData<Comment>>): ApiResult<List<Comment>> {
        return when {
            response.message().toString().contains("timeout") -> {
                ApiResult.Error("Timeout")
            }

            response.code() == 500 -> {
                ApiResult.Error(response.message())
            }

            response.isSuccessful -> {
                val result = response.body()
                if (result != null) {
                    ApiResult.Success(result.data!!)
                } else {
                    ApiResult.Error("comment not found!")
                }
            }

            else -> {
                ApiResult.Error(response.message())
            }
        }
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