package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.*
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.utils.ApiResult
import com.khue.joliecafejp.utils.Constants.Companion.CATEGORY
import com.khue.joliecafejp.utils.Constants.Companion.SEARCH
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiUseCases: ApiUseCases,
    private val dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    private var category: String

    private val _categoryProduct = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val categoryProduct: StateFlow<PagingData<Product>> = _categoryProduct

    private val _favProductsId = MutableStateFlow<ApiResult<SnapshotStateList<String>>>(ApiResult.Loading())
    val favProductsId: StateFlow<ApiResult<SnapshotStateList<String>>> = _favProductsId

    private val _addUserFavResponse =
        MutableStateFlow<ApiResult<Unit>>(ApiResult.Loading())
    val addUserFavResponse: StateFlow<ApiResult<Unit>> = _addUserFavResponse

    private val _removeUserFavResponse =
        MutableStateFlow<ApiResult<Unit>>(ApiResult.Loading())
    val removeUserFavResponse: StateFlow<ApiResult<Unit>> = _removeUserFavResponse

    val userToken  = dataStoreUseCases.readUserTokenUseCase()

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _selectedCategory: MutableState<String> =
        mutableStateOf(value = "All")
    val selectedCategory: State<String> = _selectedCategory

    init {
        category = savedStateHandle.get<String>(CATEGORY) ?: "All"
        savedStateHandle.get<String?>(SEARCH)?.let {
            _searchTextState.value = it
        }
        updateSelectedCategory(category)
    }

    fun initData(token: String) {
        val query = mutableMapOf(
            "type" to category
        )
        if(_searchTextState.value.isNotEmpty()) {
            query["name"] = _searchTextState.value
        }
        getCategoriesProducts(productQuery = query, token = token)
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun updateSelectedCategory(newValue: String) {
        _selectedCategory.value = newValue
    }

    fun getCategoriesProducts(
        productQuery: Map<String, String>,
        token: String
    ) {
        try {
            viewModelScope.launch {
                apiUseCases.getProductsUseCase(productQuery = productQuery, token = token)
                    .cachedIn(viewModelScope).collectLatest {
                    _categoryProduct.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _categoryProduct.value = PagingData.empty()
        }
    }

    fun getUserFavProductsId(
        token: String
    )  = viewModelScope.launch {
        _favProductsId.value = ApiResult.Loading()
        try {
            val response = apiUseCases.getUserFavoriteProductsIdUseCase(token = token)
            _favProductsId.value = handleApiResponse(response = response)
        } catch (e: Exception) {
            e.printStackTrace()
            _favProductsId.value = ApiResult.Error(e.message)
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

    private fun handleApiResponse(response: Response<ApiResponseMultiData<FavProductId>>): ApiResult<SnapshotStateList<String>> {
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