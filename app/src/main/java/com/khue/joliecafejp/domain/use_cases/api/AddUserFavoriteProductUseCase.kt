package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import retrofit2.Response

class AddUserFavoriteProductUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(token: String, productId: String): Response<ApiResponseSingleData<Unit>> {
        return repository.addUserFavoriteProduct(token = token , productId = productId)
    }
}