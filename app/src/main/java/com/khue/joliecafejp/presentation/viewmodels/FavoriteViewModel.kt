package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    val userToken  = dataStoreUseCases.readUserTokenUseCase()

    private val _favoriteProduct = MutableStateFlow<PagingData<FavoriteProduct>>(PagingData.empty())
    val favoriteProduct: StateFlow<PagingData<FavoriteProduct>> = _favoriteProduct

    fun getUserFavoriteProducts(
        productQuery: Map<String, String>,
        token: String
    ) {
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
    }
}