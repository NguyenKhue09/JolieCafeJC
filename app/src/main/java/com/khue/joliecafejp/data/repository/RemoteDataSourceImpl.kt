package com.khue.joliecafejp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.khue.joliecafejp.data.paging_source.ProductPagingSource
import com.khue.joliecafejp.data.remote.JolieCafeApi
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.domain.model.User
import com.khue.joliecafejp.domain.repository.RemoteDataSource
import com.khue.joliecafejp.utils.Constants.Companion.PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class RemoteDataSourceImpl(
    private val jolieCafeApi: JolieCafeApi
): RemoteDataSource{

    override suspend fun createUser(data: HashMap<String, Any>): Response<ApiResponseSingleData<User>> {
        return jolieCafeApi.createUser(body = data)
    }

    override fun getProducts(
        productQuery: Map<String, String>,
        token: String
    ): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = {
                ProductPagingSource(jolieCafeApi, token, productQuery)
            }
        ).flow
    }

}