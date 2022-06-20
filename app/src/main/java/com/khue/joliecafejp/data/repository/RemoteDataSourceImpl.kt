package com.khue.joliecafejp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.khue.joliecafejp.data.paging_source.*
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

    override suspend fun updateUserInfos(
        token: String,
        userInfos: Map<String, String>
    ): Response<ApiResponseSingleData<User>> {
        return  jolieCafeApi.updateUserInfos(token = "Bearer $token", body = userInfos)
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

    override fun getAddresses(token: String): Flow<PagingData<Address>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                AddressPagingSource(jolieCafeApi, "Bearer $token")
            }
        ).flow
    }

    override suspend fun updateAddress(
        newAddressData: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Address>> {
        return jolieCafeApi.updateAddress(body = newAddressData, token = "Bearer $token")
    }

    override suspend fun deleteAddress(
        addressId: String,
        token: String
    ): Response<ApiResponseSingleData<Address>> {
       return jolieCafeApi.deleteAddress(addressId = addressId, token = "Bearer $token")
    }

    override suspend fun addNewDefaultAddress(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<User>> {
        return jolieCafeApi.addNewDefaultAddress(body = data, token = "Bearer $token")
    }

    override suspend fun addNewAddress(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Address>> {
        return jolieCafeApi.addNewAddress(body = data, token = "Bearer $token")
    }

    override suspend fun addProductToCart(
        data: Map<String, String>,
        token: String
    ): Response<ApiResponseSingleData<Unit>> {
        return jolieCafeApi.addProductToCart(body = data, token = "Bearer $token")
    }

    override suspend fun getCartItems(token: String): Response<ApiResponseMultiData<CartItemByCategory>> {
        return jolieCafeApi.getCartItems(token = "Bearer $token")
    }

    override suspend fun getAdminNotificationForUser(
        token: String,
        tab: String
    ): Flow<PagingData<Notification>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                NotificationPagingSource(jolieCafeApi, "Bearer $token", tab)
            }
        ).flow
    }

    override fun getUserBills(token: String): Flow<PagingData<OrderHistory>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { OrderHistoryPagingSource(token = "Bearer $token", jolieCafeApi = jolieCafeApi) }
        ).flow
    }

    override suspend fun reviewBill(
        token: String,
        body: BillReviewBody
    ): Response<ApiResponseSingleData<Unit>> {
        return jolieCafeApi.reviewBill(token = "Bearer $token", body = body)
    }

    override suspend fun getProductComments(
        token: String,
        productId: String
    ): Response<ApiResponseMultiData<Comment>> {
        return jolieCafeApi.getCommentProduct( token= "Bearer $token", productId = productId)
    }

}