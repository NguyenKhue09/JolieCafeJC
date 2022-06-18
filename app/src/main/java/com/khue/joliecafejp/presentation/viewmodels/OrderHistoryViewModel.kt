package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.BillReviewBody
import com.khue.joliecafejp.domain.model.OrderHistory
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {
    val userToken = dataStoreUseCases.readUserTokenUseCase()

    private val _bills = MutableStateFlow<PagingData<OrderHistory>>(PagingData.empty())
    val bills: StateFlow<PagingData<OrderHistory>> = _bills

    var reviewBillResponse = MutableStateFlow<ApiResult<Unit>>(ApiResult.Idle())
        private set

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

    fun reviewBills(billReviewBody: BillReviewBody, token: String) =  viewModelScope.launch {
        reviewBillResponse.value = ApiResult.Loading()
        try {
            val response = apiUseCases.reviewBillUseCase(
                token = token,
                body = billReviewBody
            )
            reviewBillResponse.value = handleApiResponse(response = response)
        } catch (e: Exception) {
            e.printStackTrace()
            reviewBillResponse.value = ApiResult.Error(e.message)
        }
    }

    private fun <T> handleApiResponse(response: Response<ApiResponseSingleData<T>>): ApiResult<T> {
        println(response)
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