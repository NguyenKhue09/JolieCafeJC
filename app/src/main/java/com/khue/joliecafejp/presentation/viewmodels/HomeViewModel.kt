package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.ApiResponseMultiData
import com.khue.joliecafejp.domain.model.FavProductId
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases
) : ViewModel() {

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _bestSellerProduct = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val bestSellerProduct: StateFlow<PagingData<Product>> = _bestSellerProduct

    private val _favProductsId = MutableStateFlow<ApiResult<List<String>>>(ApiResult.Loading())
    val favProductsId: StateFlow<ApiResult<List<String>>> = _favProductsId

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


    fun getProducts(
        productQuery: Map<String, String>,
        token: String
    ) {
        try {
            viewModelScope.launch {
                apiUseCases.getProductsUseCase(productQuery = productQuery, token = token).cachedIn(viewModelScope).collectLatest {
                    _bestSellerProduct.value = it
                }
            }
        } catch (e: Exception) {
           e.printStackTrace()
           _bestSellerProduct.value = PagingData.empty()
        }
    }

    fun getUserFavProductsId(
        token: String
    )  = viewModelScope.launch {
        try {
            val response = apiUseCases.getUserFavoriteProductsIdUseCase(token = token)
            _favProductsId.value = handleApiResponse(response = response)
        } catch (e: Exception) {
            e.printStackTrace()
            _favProductsId.value = ApiResult.Error(e.message)
        }
    }

    private fun handleApiResponse(response: Response<ApiResponseMultiData<FavProductId>>): ApiResult<List<String>> {
        val result = response.body()
        return when {
            response.message().toString().contains("timeout") -> {
                ApiResult.Error("Timeout")
            }
            response.code() == 500 -> {
                ApiResult.Error(response.message())
            }
            response.isSuccessful -> {
                val listId = result!!.data!!.map {
                    it.productId
                }
                ApiResult.Success(listId)
            }
            else -> {
                ApiResult.Error(response.message())
            }
        }
    }
}