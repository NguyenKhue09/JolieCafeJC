package com.khue.joliecafejp.presentation.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.utils.Constants.Companion.CATEGORY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val apiUseCases: ApiUseCases,
    private val dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    private lateinit var category: String

    private val _categoryProduct = MutableStateFlow<PagingData<Product>>(PagingData.empty())
    val categoryProduct: StateFlow<PagingData<Product>> = _categoryProduct

    val userToken  = dataStoreUseCases.readUserTokenUseCase()

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _selectedCategory: MutableState<String> =
        mutableStateOf(value = "All")
    val selectedCategory: State<String> = _selectedCategory

    init {
        category = savedStateHandle.get<String>(CATEGORY) ?: "All"
        updateSelectedCategory(category)
    }

    fun initData(token: String) {
        getCategoriesProducts(productQuery = mapOf("type" to category), token = token)
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
}