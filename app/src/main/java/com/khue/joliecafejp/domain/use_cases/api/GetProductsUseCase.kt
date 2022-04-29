package com.khue.joliecafejp.domain.use_cases.api

import androidx.paging.PagingData
import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.Product
import kotlinx.coroutines.flow.Flow

class GetProductsUseCase(
    private val repository: Repository
) {
    operator fun invoke(
        productQuery: Map<String, String>,
        token: String
    ): Flow<PagingData<Product>> {
        return repository.getProducts(productQuery = productQuery, token = token)
    }
}