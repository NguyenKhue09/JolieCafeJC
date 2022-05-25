package com.khue.joliecafejp.data.paging_source

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.khue.joliecafejp.data.remote.JolieCafeApi
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.Product
import com.khue.joliecafejp.utils.Constants
import retrofit2.HttpException
import java.io.IOException

class AddressPagingSource(
    private val jolieCafeApi: JolieCafeApi,
    private val token: String
): PagingSource<Int, Address>() {
    override fun getRefreshKey(state: PagingState<Int, Address>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Address> {
        try {
            val nextPageNumber = params.key ?: 1
            val query = mapOf(
                "currentPage" to nextPageNumber.toString(),
                "addressPerPage" to Constants.PAGE_SIZE.toString(),
            )

            println (nextPageNumber)

            try {
                val response = jolieCafeApi.getAddresses(token = token, addressQuery = query)

                Log.d("Bug", response.data.isNullOrEmpty().toString())

                return if(response.success) {
                    LoadResult.Page(
                        data = if(response.data.isNullOrEmpty()) emptyList() else response.data,
                        prevKey = if(response.data.isNullOrEmpty()) null else response.prevPage,
                        nextKey = if(response.data.isNullOrEmpty()) null else response.nextPage
                    )
                } else {
                    LoadResult.Error(throwable = Throwable(response.message))
                }

            } catch (e: Exception) {
                e.printStackTrace()
                return LoadResult.Error(throwable = Throwable(e.message))
            }

        } catch (e: IOException) {
            // IOException for network failures.
            return LoadResult.Error(e)
        } catch (e: HttpException) {
            // HttpException for any non-2xx HTTP status codes.
            return LoadResult.Error(e)
        }
    }

}