package com.khue.joliecafejp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.khue.joliecafejp.data.paging_source.FavoriteProductPagingSource
import com.khue.joliecafejp.data.paging_source.ProductPagingSource
import com.khue.joliecafejp.data.remote.JolieCafeApi
import com.khue.joliecafejp.domain.model.*
import com.khue.joliecafejp.domain.repository.RemoteDataSource
import com.khue.joliecafejp.utils.Constants.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RemoteDataSourceImpl(
    private val jolieCafeApi: JolieCafeApi
): RemoteDataSource{

    override suspend fun createUser(data: Map<String, String>): Response<ApiResponseSingleData<User>> {
        return jolieCafeApi.createUser(body = data)
    }

    override suspend fun userLogin(userId: String): Response<ApiResponseSingleData<User>> {
        return jolieCafeApi.userLogin(userId = userId)
    }

    override suspend fun getUserInfos(token: String): Response<ApiResponseSingleData<User>> {
        return jolieCafeApi.getUserInfos(token = "Bearer $token")
    }

    override fun getProducts(
        productQuery: Map<String, String>,
        token: String
    ): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                ProductPagingSource(jolieCafeApi, "Bearer $token", productQuery)
            }
        ).flow
    }

    override suspend fun getProductDetail(
        token: String,
        productId: String
    ): Response<ApiResponseSingleData<Product>> {
        return jolieCafeApi.getProductDetail(productId = productId, token = "Bearer $token")
    }

    override fun getUserFavoriteProducts(
        productQuery: Map<String, String>,
        token: String
    ): Flow<PagingData<FavoriteProduct>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                FavoriteProductPagingSource(jolieCafeApi, "Bearer $token", productQuery)
            }
        ).flow
    }

    override suspend fun getUserFavoriteProductsId(token: String): Response<ApiResponseMultiData<FavProductId>> {
        return jolieCafeApi.getUserFavoriteProductsId(token = "Bearer $token")
    }

    override suspend fun addUserFavoriteProduct(
        token: String,
        productId: String
    ): Response<ApiResponseSingleData<Unit>> {
        return jolieCafeApi.addUserFavoriteProduct(token = "Bearer $token", productId = productId)
    }

    override suspend fun removeUserFavoriteProduct(
        token: String,
        favoriteProductId: String
    ): Response<ApiResponseSingleData<Unit>> {
        return jolieCafeApi.removeUserFavoriteProduct(token = "Bearer $token", favoriteProductId = favoriteProductId)
    }

    override suspend fun removeUserFavoriteProductByProductId(
        token: String,
        productId: String
    ): Response<ApiResponseSingleData<Unit>> {
        return jolieCafeApi.removeUserFavoriteProductByProductId(
            token = "Bearer $token", productId = productId
        )
    }
}