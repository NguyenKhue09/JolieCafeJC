package com.khue.joliecafejp.data.remote

import com.khue.joliecafejp.domain.model.*
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
    @POST("/api/v1/jolie-cafe/user/create-new-user")
    suspend fun createUser(
        @Body body: HashMap<String, Any>,
    ): Response<ApiResponseSingleData<User>>

    @GET("/api/v1/jolie-cafe/product/get-products")
    suspend fun getProducts(
        @QueryMap productQuery: Map<String, String>,
        @Header("Authorization") token: String
    ): ApiResponseMultiData<Product>

   // https://stackoverflow.com/questions/41078866/retrofit2-authorization-global-interceptor-for-access-token

}