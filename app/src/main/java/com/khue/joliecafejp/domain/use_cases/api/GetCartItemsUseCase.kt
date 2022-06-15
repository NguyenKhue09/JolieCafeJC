package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.ApiResponseMultiData
import com.khue.joliecafejp.domain.model.CartItemByCategory
import retrofit2.Response

class GetCartItemsUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(token: String): Response<ApiResponseMultiData<CartItemByCategory>> {
        return repository.getCartItems(token)
    }
}