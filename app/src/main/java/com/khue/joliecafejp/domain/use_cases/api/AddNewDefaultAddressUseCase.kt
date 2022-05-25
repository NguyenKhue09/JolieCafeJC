package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.User
import retrofit2.Response

class AddNewDefaultAddressUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<User>> {
        return repository.addNewDefaultAddress(data = data, token = token)
    }
}