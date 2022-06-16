package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.google.android.gms.common.api.Api
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.User
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    val userToken  = dataStoreUseCases.readUserTokenUseCase()

    private val _favoriteProduct = MutableStateFlow<PagingData<FavoriteProduct>>(PagingData.empty())
    val favoriteProduct: StateFlow<PagingData<FavoriteProduct>> = _favoriteProduct

    private val _removeUserFavProductResponse = MutableStateFlow<ApiResult<Unit>>(ApiResult.Loading())
    val removeUserFavProductResponse: StateFlow<ApiResult<Unit>> = _removeUserFavProductResponse



    fun getUserFavoriteProducts(
        productQuery: Map<String, String>,
        token: String
    ) =
         try {
             viewModelScope.launch {
                 apiUseCases.getUserFavoriteProductsUseCase(
                     productQuery = productQuery,
                     token = token
                 ).cachedIn(viewModelScope).collectLatest {
                    _favoriteProduct.value = it
                 }
             }
        } catch (e: Exception) {
            e.printStackTrace()
             _favoriteProduct.value = PagingData.empty()
        }




    fun removeUserFavoriteProduct(token: String, favoriteProductId: String) =
        viewModelScope.launch {
            _removeUserFavProductResponse.value = ApiResult.Loading()
            try {
                if (token.isEmpty()) Throwable("Unauthorized")
                val response = apiUseCases.removeUserFavProduct(
                    token = token,
                    favoriteProductId = favoriteProductId
                )
                _removeUserFavProductResponse.value = handleApiResponse(response = response)
            } catch (e: Exception) {
                e.printStackTrace()
                _removeUserFavProductResponse.value = ApiResult.Error(e.message)
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
                    ApiResult.NullDataSuccess()
            }
            else -> {
                ApiResult.Error(response.message())
            }
        }
    }
}