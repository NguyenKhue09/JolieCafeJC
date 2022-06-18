package com.khue.joliecafejp.data.remote

import com.khue.joliecafejp.domain.model.*
import com.khue.joliecafejp.utils.Constants.Companion.API_GATEWAY
import retrofit2.Response
import retrofit2.http.*

interface JolieCafeApi {

    @Headers("Content-Type: application/json")
    @POST("/api/v1/jolie-cafe/payment/payment-request")
    suspend fun momoRequestPayment(
        @Body body: HashMap<String, Any>,
        @Header("Authorization") token: String
    ): Response<SuspendUserMoneyResponse>

    @Headers("Content-Type: application/json")
    @POST("$API_GATEWAY/user/create-new-user")
    suspend fun createUser(
        @Body body: Map<String, String>,
    ): Response<ApiResponseSingleData<User>>

    @GET("$API_GATEWAY/user/login")
    suspend fun userLogin(
        @Query("userId") userId: String
    ): Response<ApiResponseSingleData<User>>

    @GET("$API_GATEWAY/user/get-user-info")
    suspend fun getUserInfos(
        @Header("Authorization") token: String
    ): Response<ApiResponseSingleData<User>>

    @PUT("$API_GATEWAY/user/update-user-info")
    suspend fun updateUserInfos(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>,
    ): Response<ApiResponseSingleData<User>>


    @GET("$API_GATEWAY/product/get-products")
    suspend fun getProducts(
        @QueryMap productQuery: Map<String, String>,
        @Header("Authorization") token: String
    ): ApiResponseMultiData<Product>

    @GET("$API_GATEWAY/product/get-product-detail")
    suspend fun getProductDetail(
        @Query("productId") productId: String,
        @Header("Authorization") token: String
    ): Response<ApiResponseSingleData<Product>>

    @Headers("Content-Type: application/json")
    @GET("$API_GATEWAY/favorite")
    suspend fun getUserFavoriteProduct(
        @QueryMap productQuery: Map<String, String>,
        @Header("Authorization") token: String
    ): ApiResponseMultiData<FavoriteProduct>

    @GET("$API_GATEWAY/favorite/list-id")
    suspend fun getUserFavoriteProductsId(
        @Header("Authorization") token: String
    ):  Response<ApiResponseMultiData<FavProductId>>

    @DELETE("$API_GATEWAY/favorite/remove")
    suspend fun removeUserFavoriteProduct(
        @Header("Authorization") token: String,
        @Query("favoriteProductId") favoriteProductId: String,
    ): Response<ApiResponseSingleData<Unit>>

    @DELETE("$API_GATEWAY/favorite/remove-by-productId")
    suspend fun removeUserFavoriteProductByProductId(
        @Header("Authorization") token: String,
        @Query("productId") productId: String,
    ): Response<ApiResponseSingleData<Unit>>

    @POST("$API_GATEWAY/favorite/add")
    suspend fun addUserFavoriteProduct(
        @Header("Authorization") token: String,
        @Query("productId") productId: String,
    ): Response<ApiResponseSingleData<Unit>>

    @Headers("Content-Type: application/json")
    @POST("$API_GATEWAY/address/add")
    suspend fun addNewAddress(
        @Body body: Map<String, String>,
        @Header("Authorization") token: String
    ): Response<ApiResponseSingleData<Address>>

    @Headers("Content-Type: application/json")
    @POST("$API_GATEWAY/address/add/default")
    suspend fun addNewDefaultAddress(
        @Body body: Map<String, String>,
        @Header("Authorization") token: String
    ): Response<ApiResponseSingleData<User>>

    @Headers("Content-Type: application/json")
    @GET("$API_GATEWAY/address/get")
    suspend fun getAddresses(
        @Header("Authorization") token: String,
        @QueryMap addressQuery: Map<String, String>,
    ): ApiResponseMultiData<Address>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("$API_GATEWAY/address/delete")
    @FormUrlEncoded
    suspend fun deleteAddress(
        @Field("addressId") addressId: String,
        @Header("Authorization") token: String
    ): Response<ApiResponseSingleData<Address>>

    @Headers("Content-Type: application/json")
    @POST("$API_GATEWAY/address/update")
    suspend fun updateAddress(
        @Body body: Map<String, String>,
        @Header("Authorization") token: String
    ): Response<ApiResponseSingleData<Address>>

    // Cart API
    @Headers("Content-Type: application/json")
    @GET("$API_GATEWAY/cart")
    suspend fun getCartItems(
        @Header("Authorization") token: String,
        @QueryMap cartQuery: Map<String, String>,
    ): ApiResponseMultiData<CartItem>

    @Headers("Content-Type: application/json")
    @PUT("$API_GATEWAY/cart/update")
    suspend fun updateCartItem(
        @Header("Authorization") token: String,
        @Body body: Map<String, String>,
    ): Response<ApiResponseSingleData<CartItem>>

    @Headers("Content-Type: application/json")
    @DELETE("$API_GATEWAY/cart/remove/{productId}")
    suspend fun deleteCartItem(
        @Header("Authorization") token: String,
        @Path("productId") productId: String,
    ): Response<ApiResponseSingleData<Unit>>

    @Headers("Content-Type: application/json")
    @POST("$API_GATEWAY/cart/add")
    suspend fun addProductToCart(
        @Body body: Map<String, String>,
        @Header("Authorization") token: String
    ): Response<ApiResponseSingleData<Unit>>

    @Headers("Content-Type: application/json")
    @GET("$API_GATEWAY/cart/get-cart-v2")
    suspend fun getCartItems(
        @Header("Authorization") token: String
    ): Response<ApiResponseMultiData<CartItemByCategory>>
   // https://stackoverflow.com/questions/41078866/retrofit2-authorization-global-interceptor-for-access-token

    @GET("$API_GATEWAY/notification/all-admin-notice-user")
    suspend fun getAdminNotificationForUser(
        @QueryMap notificationQuery: Map<String, String>,
        @Header("Authorization") token: String
    ): ApiResponseMultiData<Notification>

    @GET("$API_GATEWAY/notification/all-user-notice")
    suspend fun getAllUserNotification(
        @QueryMap notificationQuery: Map<String, String>,
        @Header("Authorization") token: String
    ): ApiResponseMultiData<Notification>

    @Headers("Content-Type: application/json")
    @GET("$API_GATEWAY/bill/get-bill-user")
    suspend fun getUserBills(
        @Header("Authorization") token: String,
        @QueryMap orderQuery: Map<String, String>,
    ): ApiResponseMultiData<OrderHistory>
}