package com.khue.joliecafejp.domain.repository

import androidx.paging.PagingData
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.FavoriteProduct
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
    suspend fun createUser(data: Map<String, String>): Response<ApiResponseSingleData<User>>
    suspend fun userLogin(userId: String): Response<ApiResponseSingleData<User>>
    suspend fun getUserInfos(token: String): Response<ApiResponseSingleData<User>>

    fun getProducts(productQuery: Map<String, String>, token: String): Flow<PagingData<Product>>
    fun getUserFavoriteProducts(productQuery: Map<String, String>, token: String): Flow<PagingData<FavoriteProduct>>
}