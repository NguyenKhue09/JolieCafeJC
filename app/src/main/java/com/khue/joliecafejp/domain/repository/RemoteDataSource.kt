package com.khue.joliecafejp.domain.repository

import androidx.paging.PagingData
import com.khue.joliecafejp.domain.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
    suspend fun createUser(data: Map<String, String>): Response<ApiResponseSingleData<User>>
    suspend fun userLogin(userId: String): Response<ApiResponseSingleData<User>>
    suspend fun getUserInfos(token: String): Response<ApiResponseSingleData<User>>

    fun getProducts(productQuery: Map<String, String>, token: String): Flow<PagingData<Product>>
    suspend fun getProductDetail(token: String, productId: String): Response<ApiResponseSingleData<Product>>
    fun getUserFavoriteProducts(productQuery: Map<String, String>, token: String): Flow<PagingData<FavoriteProduct>>

    suspend fun getUserFavoriteProductsId(
        token: String,
    ): Response<ApiResponseMultiData<FavProductId>>

    suspend fun addUserFavoriteProduct(
        token: String,
        productId: String
    ): Response<ApiResponseSingleData<Unit>>

    suspend  fun removeUserFavoriteProduct(
        token: String,
        favoriteProductId: String,
    ): Response<ApiResponseSingleData<Unit>>
}