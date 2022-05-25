package com.khue.joliecafejp.domain.use_cases.api

import androidx.paging.PagingData
import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class GetAddressesUseCase(
    private val repository: Repository
) {
    operator fun invoke(
        token: String
    ): Flow<PagingData<Address>> {
        return repository.getAddresses(token = token)
    }
}