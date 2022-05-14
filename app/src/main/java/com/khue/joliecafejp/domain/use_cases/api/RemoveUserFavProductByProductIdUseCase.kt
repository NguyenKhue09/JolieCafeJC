package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.User
import retrofit2.Response

class RemoveUserFavProductByProductIdUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(token: String, productId: String): Response<ApiResponseSingleData<Unit>> {
        return repository.removeUserFavoriteProductByProductId(productId = productId, token = token)
    }
}