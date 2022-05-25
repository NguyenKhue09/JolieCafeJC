package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import retrofit2.Response

class DeleteAddressUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        addressId: String,
        token: String
    ): Response<ApiResponseSingleData<Address>> {
        return repository.deleteAddress(addressId = addressId, token = token)
    }
}