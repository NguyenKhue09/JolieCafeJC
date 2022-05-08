package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.User
import retrofit2.Response

class RemoveUserFavProductUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(token: String, favoriteProductId: String): Response<ApiResponseSingleData<Unit>> {
        return repository.removeUserFavoriteProduct(favoriteProductId = favoriteProductId, token = token)
    }
}