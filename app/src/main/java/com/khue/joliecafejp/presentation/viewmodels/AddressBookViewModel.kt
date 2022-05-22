package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddressBookViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    val userToken = dataStoreUseCases.readUserTokenUseCase()

    private val _addressBook = MutableStateFlow<PagingData<Address>>(PagingData.empty())
    val addressBook: StateFlow<PagingData<Address>> = _addressBook


}