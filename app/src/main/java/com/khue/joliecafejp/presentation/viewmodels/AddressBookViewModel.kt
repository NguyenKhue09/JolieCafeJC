package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressBookViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    val userToken = dataStoreUseCases.readUserTokenUseCase()

    private val _addressBooks = MutableStateFlow<PagingData<Address>>(PagingData.empty())
    val addressBooks: StateFlow<PagingData<Address>> = _addressBooks

    fun getProducts(
        token: String
    ) {
        try {
            viewModelScope.launch {
                apiUseCases.getAddresses(token = token).cachedIn(viewModelScope).collectLatest {
                    _addressBooks.value = it
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _addressBooks.value = PagingData.empty()
        }
    }

}