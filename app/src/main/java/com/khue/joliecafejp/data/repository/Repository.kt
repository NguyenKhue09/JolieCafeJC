package com.khue.joliecafejp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.khue.joliecafejp.data.paging_source.OrderHistoryPagingSource
import com.khue.joliecafejp.domain.model.*
import com.khue.joliecafejp.domain.repository.DataStoreOperations
import com.khue.joliecafejp.domain.repository.RemoteDataSource
import com.khue.joliecafejp.utils.Constants
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {
    suspend fun createUser(data: Map<String, String>): Response<ApiResponseSingleData<User>> {
        return remote.createUser(data = data)
    }

    suspend fun userLogin(userId: String): Response<ApiResponseSingleData<User>> {
        return remote.userLogin(userId = userId)
    }

    suspend fun getUserInfos(token: String): Response<ApiResponseSingleData<User>> {
        return remote.getUserInfos(token = token)
    }

    suspend fun updateUserInfos(token: String, userInfos: Map<String, String>): Response<ApiResponseSingleData<User>> {
        return remote.updateUserInfos(token = token, userInfos = userInfos)
    }

    fun getProducts(
        productQuery: Map<String, String>,
        token: String
    ): Flow<PagingData<Product>> {
        return remote.getProducts(productQuery = productQuery, token = token)
    }

    suspend fun getProductDetail(
        productId: String,
        token: String
    ): Response<ApiResponseSingleData<Product>> {
        return remote.getProductDetail(productId = productId, token = token)
    }

    fun getUserFavoriteProducts(
        productQuery: Map<String, String>,
        token: String
    ): Flow<PagingData<FavoriteProduct>> {
        return remote.getUserFavoriteProducts(productQuery = productQuery, token = token)
    }

    suspend fun getUserFavoriteProductsId(
        token: String
    ): Response<ApiResponseMultiData<FavProductId>> {
        return remote.getUserFavoriteProductsId(token = token)
    }

    suspend fun addUserFavoriteProduct(
       token: String,
       productId: String
    ): Response<ApiResponseSingleData<Unit>> {
        return remote.addUserFavoriteProduct(token = token, productId = productId)
    }

    suspend fun removeUserFavoriteProduct(
        token: String,
        favoriteProductId: String
    ): Response<ApiResponseSingleData<Unit>> {
        return remote.removeUserFavoriteProduct(token = token, favoriteProductId = favoriteProductId)
    }

    suspend fun removeUserFavoriteProductByProductId(
        token: String,
        productId: String
    ): Response<ApiResponseSingleData<Unit>> {
        return remote.removeUserFavoriteProductByProductId(token = token, productId = productId)
    }


    suspend fun saveUserToken(userToken: String) {
        dataStore.saveUserToken(userToken = userToken)
    }

    fun readUserToken(): Flow<String> {
        return dataStore.readUserToken()
    }

    fun getAddresses(
        token: String
    ): Flow<PagingData<Address>> {
        return remote.getAddresses(token = token)
    }

    suspend fun updateAddress(
        newAddressData: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Address>> {
        return remote.updateAddress(newAddressData = newAddressData, token = token)
    }

    suspend fun deleteAddress(
        addressId: String,
        token: String
    ): Response<ApiResponseSingleData<Address>> {
        return remote.deleteAddress(addressId = addressId, token = token)
    }

    suspend fun addNewDefaultAddress(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<User>> {
        return remote.addNewDefaultAddress(data = data, token = token)
    }

    suspend fun addNewAddress(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Address>> {
        return remote.addNewAddress(data = data, token = token)
    }

    suspend fun addProductToCart(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Unit>> {
        return remote.addProductToCart(data = data, token = token)
    }

    suspend fun getCartItems(
        token: String
    ): Response<ApiResponseMultiData<CartItemByCategory>> {
        return remote.getCartItems(token = token)
    }

    suspend fun getAdminNotificationForUser(
        token: String,
        tab: String
    ): Flow<PagingData<Notification>> {
        return remote.getAdminNotificationForUser(token = token, tab = tab)
    }

    fun getUserBills(token: String): Flow<PagingData<OrderHistory>> {
        return remote.getUserBills(token = token)
    }
}