package com.khue.joliecafejp.domain.use_cases.api

import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.model.ApiResponseSingleData
import com.khue.joliecafejp.domain.model.User
import retrofit2.Response

class GetUserInfosUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(token: String): Response<ApiResponseSingleData<User>> {
        return repository.getUserInfos(token = token)
    }
}