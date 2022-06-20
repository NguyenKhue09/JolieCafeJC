package com.khue.joliecafejp.domain.use_cases.api

import androidx.paging.PagingData
import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.ApiResponseMultiData
import com.khue.joliecafejp.domain.model.Comment
import com.khue.joliecafejp.domain.model.Product
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class GetProductCommentUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        token: String, productId: String
    ): Response<ApiResponseMultiData<Comment>> {
        return repository.getProductComments(token, productId)
    }
}