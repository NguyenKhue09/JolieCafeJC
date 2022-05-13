package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.ApiResponseMultiData
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.FavProductId
import retrofit2.Response

class GetUserFavoriteProductsIdUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(token: String): Response<ApiResponseMultiData<FavProductId>> {
        return repository.getUserFavoriteProductsId(token = token)
    }
}