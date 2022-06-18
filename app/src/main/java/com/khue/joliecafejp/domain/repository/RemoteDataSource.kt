package com.khue.joliecafejp.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.khue.joliecafejp.domain.model.*
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
    suspend fun createUser(data: Map<String, String>): Response<ApiResponseSingleData<User>>
    suspend fun userLogin(userId: String): Response<ApiResponseSingleData<User>>
    suspend fun getUserInfos(token: String): Response<ApiResponseSingleData<User>>
    suspend fun updateUserInfos(token: String, userInfos: Map<String, String>): Response<ApiResponseSingleData<User>>

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

    suspend  fun removeUserFavoriteProductByProductId(
        token: String,
        productId: String,
    ): Response<ApiResponseSingleData<Unit>>

    fun getAddresses(token: String): Flow<PagingData<Address>>

    suspend fun updateAddress(
        newAddressData: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Address>>

    suspend fun deleteAddress(
        addressId: String,
        token: String
    ): Response<ApiResponseSingleData<Address>>

    suspend fun addNewDefaultAddress(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<User>>

    suspend fun addNewAddress(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Address>>

    suspend fun addProductToCart(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Unit>>

    suspend fun getCartItems(
        token: String
    ): Response<ApiResponseMultiData<CartItemByCategory>>

    suspend fun getAdminNotificationForUser(
        token: String,
        tab: String
    ): Flow<PagingData<Notification>>

    fun getUserBills(token: String): Flow<PagingData<OrderHistory>>

    suspend fun reviewBill(token: String, body: BillReviewBody): Response<ApiResponseSingleData<Unit>>
}