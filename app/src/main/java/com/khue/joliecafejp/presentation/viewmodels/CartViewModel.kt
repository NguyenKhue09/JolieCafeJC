package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import com.khue.joliecafejp.domain.model.User
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.domain.use_cases.ValidationUseCases
import com.khue.joliecafejp.utils.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CartViewModel@Inject constructor(
    private val apiUseCases: ApiUseCases,
    private val validationUseCases: ValidationUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    var x = 1
    private val _userLoginResponseTest = MutableStateFlow<ApiResult<Int>>(ApiResult.Loading())
    val userLoginResponseTest: StateFlow<ApiResult<Int>> = _userLoginResponseTest

    fun setData() {
        x++
        _userLoginResponseTest.value = ApiResult.Success(data = x)
    }
}