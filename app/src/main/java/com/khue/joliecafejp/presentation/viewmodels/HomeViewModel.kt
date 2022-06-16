package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.*
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.utils.AddProductToCartEvent
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

    private val _addProductToCartResponse = MutableStateFlow<ApiResult<Unit>>(ApiResult.Idle())
    val addProductToCartResponse: StateFlow<ApiResult<Unit>> = _addProductToCartResponse

    val addProductToCartState = MutableStateFlow(AddProductToCartState())

    private val productEventChannel = Channel<ProductEvent>()
    val productEvent = productEventChannel.receiveAsFlow()

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

    fun addProductToCart(
       data: Map<String, String>,
       token: String
    ) = viewModelScope.launch {
        try {
            _addProductToCartResponse.value = ApiResult.Loading()
            val response = apiUseCases.addProductToCartUseCase(data = data, token = token)
            _addProductToCartResponse.value = handleNullDataApiResponse(response = response)
        } catch (e: Exception) {
            e.printStackTrace()
            _addProductToCartResponse.value = ApiResult.Error(e.message)
        }
    }

    fun onAddProductToCart(event: AddProductToCartEvent) {
        when(event) {
            is AddProductToCartEvent.SizeChanged -> {
                addProductToCartState.value = addProductToCartState.value.copy(size = event.size)
            }
            is AddProductToCartEvent.SugarChanged -> {
                addProductToCartState.value = addProductToCartState.value.copy(sugar = event.sugar)
            }
            is AddProductToCartEvent.IceChanged -> {
                addProductToCartState.value = addProductToCartState.value.copy(ice = event.ice)
            }
            is AddProductToCartEvent.AddTopping-> {
                addProductToCartState.value.addTopping(event.topping)
            }
            is AddProductToCartEvent.RemoveTopping-> {
                addProductToCartState.value.removeTopping(event.topping)
            }
            is AddProductToCartEvent.OnNoteChanged -> {
                addProductToCartState.value = addProductToCartState.value.copy(note = event.note)
            }
            is AddProductToCartEvent.AddToCart -> {
                viewModelScope.launch {
                    productEventChannel.send(ProductEvent.AddToCart)
                }
            }
            is AddProductToCartEvent.Purchase -> {
                viewModelScope.launch {
                    productEventChannel.send(ProductEvent.Purchase)
                }
            }
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
                val listId = result!!.data?.map {
                    it.productId
                } ?: emptyList()
                ApiResult.Success(listId)
            }
            else -> {
                ApiResult.Error(response.message())
            }
        }
    }

    private fun handleNullDataApiResponse(response: Response<ApiResponseSingleData<Unit>>): ApiResult<Unit> {
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

    fun resetAddProductToCartResponse() {
        _addProductToCartResponse.value = ApiResult.Idle()
    }

    sealed class ProductEvent {
        object AddToCart: ProductEvent()
        object Purchase: ProductEvent()
    }
}