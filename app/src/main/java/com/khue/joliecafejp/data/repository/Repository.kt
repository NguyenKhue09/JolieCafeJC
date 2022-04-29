package com.khue.joliecafejp.data.repository

import androidx.paging.PagingData
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.User
import com.khue.joliecafejp.domain.repository.DataStoreOperations
import com.khue.joliecafejp.domain.repository.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val remote: RemoteDataSource,
    private val dataStore: DataStoreOperations
) {
    suspend fun createUser(data: HashMap<String, Any>): Response<ApiResponseSingleData<User>> {
        return remote.createUser(data = data)
    }

    fun getProducts(
        productQuery: Map<String, String>,
        token: String
    ): Flow<PagingData<Product>> {
        return remote.getProducts(productQuery = productQuery, token = token)
    }

    suspend fun saveUserToken(userToken: String) {
        dataStore.saveUserToken(userToken = userToken)
    }

    fun readUserToken(): Flow<String> {
        return dataStore.readUserToken()
    }

}