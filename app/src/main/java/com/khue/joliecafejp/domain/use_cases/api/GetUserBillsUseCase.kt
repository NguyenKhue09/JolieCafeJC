package com.khue.joliecafejp.domain.use_cases.api

import androidx.paging.PagingData
import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.Bill
import com.khue.joliecafejp.domain.model.Notification
import com.khue.joliecafejp.domain.model.OrderHistory
import com.khue.joliecafejp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class GetUserBillsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        token: String
    ): Flow<PagingData<OrderHistory>> {
        return repository.getUserBills(token)
    }
}