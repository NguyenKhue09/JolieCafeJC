package com.khue.joliecafejp.domain.repository

import androidx.paging.PagingData
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
    suspend fun createUser(data: HashMap<String, Any>): Response<ApiResponseSingleData<User>>
    fun getProducts(productQuery: Map<String, String>, token: String): Flow<PagingData<Product>>
}