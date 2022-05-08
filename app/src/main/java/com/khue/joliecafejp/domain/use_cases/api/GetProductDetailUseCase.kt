package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.User
import retrofit2.Response

class GetProductDetailUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(token: String, productId: String): Response<ApiResponseSingleData<Product>> {
        return repository.getProductDetail(productId = productId, token = token)
    }
}