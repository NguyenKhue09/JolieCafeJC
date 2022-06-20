package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.khue.joliecafejp.domain.model.*
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.utils.AddProductToCartEvent
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants.Companion.IS_FAV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
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

    private val _addProductToCartResponse = MutableStateFlow<ApiResult<Unit>>(ApiResult.Idle())
    val addProductToCartResponse: StateFlow<ApiResult<Unit>> = _addProductToCartResponse

    private var moreProducts = MutableStateFlow<PagingData<Product>>(PagingData.empty())

    private var favProductsId = MutableStateFlow<ApiResult<SnapshotStateList<String>>>(ApiResult.Loading())

    var moreProductsWithFav = MutableStateFlow<PagingData<Product>>(PagingData.empty())
        private set

    val addProductToCartState = MutableStateFlow(AddProductToCartState())

    private val productEventChannel = Channel<ProductEvent>()
    val productEvent = productEventChannel.receiveAsFlow()


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

    fun combineFavProductsIdWithMoreProducts() = viewModelScope.launch {
         moreProducts.combine(favProductsId) { products, favs ->
            products.map { product ->
                if(favs is ApiResult.Success) {
                    if(favs.data.isNullOrEmpty()) {
                        product
                    } else {
                        product.copy(isFavorite = favs.data.contains(product.id))
                    }
                } else {
                    product
                }
            }
        }.collect {
             moreProductsWithFav.value = it
         }
    }

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

    fun getUserFavProductsId(
        token: String
    )  = viewModelScope.launch {
        favProductsId.value = ApiResult.Loading()
        try {
            val response = apiUseCases.getUserFavoriteProductsIdUseCase(token = token)
            favProductsId.value = handleGetFavProductIdApiResponse(response = response)
        } catch (e: Exception) {
            e.printStackTrace()
            favProductsId.value = ApiResult.Error(e.message)
        }
    }

    fun setFavProductState(isFav: Boolean) {
        _isFav.value = isFav
    }

    fun removeOrAddMoreProductToFav(token: String, productId: String, isFav: Boolean) {
        if(isFav) {
            removeUserFavProduct(token = token, productId = productId)
        } else {
            addUserFavProduct(token = token, productId = productId)
        }
    }

    fun removeOrAddMoreProductToFavList(productId: String, isAdd: Boolean) {
        if (isAdd) {
            favProductsId.update { result ->
                result.data!!.add(productId)
                ApiResult.Success(result.data)
            }
        } else {
            favProductsId.update { result ->
                result.data!!.remove(productId)
                ApiResult.Success(result.data)
            }
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

    private fun handleGetFavProductIdApiResponse(response: Response<ApiResponseMultiData<FavProductId>>): ApiResult<SnapshotStateList<String>> {
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
                }?.toMutableStateList() ?: mutableStateListOf()
                ApiResult.Success(listId)
            }
            else -> {
                ApiResult.Error(response.message())
            }
        }
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

    sealed class ProductEvent {
        object AddToCart: ProductEvent()
        object Purchase: ProductEvent()
    }
}