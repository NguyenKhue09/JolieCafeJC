package com.khue.joliecafejp.domain.use_cases.api

import androidx.paging.PagingData
import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.Notification
import com.khue.joliecafejp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class GetAdminNotificationForUserUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        token: String,
        tab: String
    ): Flow<PagingData<Notification>> {
        return repository.getAdminNotificationForUser(token = token, tab = tab)
    }
}