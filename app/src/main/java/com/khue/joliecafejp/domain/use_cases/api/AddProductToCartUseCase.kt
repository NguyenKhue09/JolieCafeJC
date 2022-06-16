package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import retrofit2.Response

class AddProductToCartUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Unit>> {
        return repository.addProductToCart(data = data, token = token)
    }
}