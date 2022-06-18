package com.khue.joliecafejp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.domain.model.Notification
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationVieModel @Inject constructor(
    private val apiUseCases: ApiUseCases,
    dataStoreUseCases: DataStoreUseCases
) : ViewModel() {

    val userToken = dataStoreUseCases.readUserTokenUseCase()

    private val _notifications = MutableStateFlow<PagingData<Notification>>(PagingData.empty())
    val notifications: StateFlow<PagingData<Notification>> = _notifications

    fun getNotifications(
        token: String,
        tab: String
    ) = try {
        viewModelScope.launch {
            apiUseCases.getAdminNotificationForUserUseCase(token, tab)
                .cachedIn(viewModelScope)
                .collect {
                    _notifications.value = it
                }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        _notifications.value = PagingData.empty()
    }

}