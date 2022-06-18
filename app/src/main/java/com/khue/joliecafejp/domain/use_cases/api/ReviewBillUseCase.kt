package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.Address
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.BillReviewBody
import retrofit2.Response

class ReviewBillUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(
        token: String,
        body: BillReviewBody
    ): Response<ApiResponseSingleData<Unit>> {
        return repository.reviewBill(token, body)
    }
}