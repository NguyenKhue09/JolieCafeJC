package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.OrderHistory
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {
    val userToken = dataStoreUseCases.readUserTokenUseCase()

    private val _bills = MutableStateFlow<PagingData<OrderHistory>>(PagingData.empty())
    val bills: StateFlow<PagingData<OrderHistory>> = _bills

    fun getUserBills(
        token: String
    ) = try {
        viewModelScope.launch {
            apiUseCases.getUserBillsUseCase(token)
                .cachedIn(viewModelScope)
                .collect {
                    _bills.value = it
                }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        _bills.value = PagingData.empty()
    }
}