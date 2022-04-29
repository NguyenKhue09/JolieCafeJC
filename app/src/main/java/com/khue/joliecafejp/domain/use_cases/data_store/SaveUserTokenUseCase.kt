package com.khue.joliecafejp.domain.use_cases.data_store

import com.khue.joliecafejp.data.repository.Repository

class SaveUserTokenUseCase(
    private val repository: Repository
) {
    suspend operator fun invoke(userToken: String) {
        repository.saveUserToken(userToken = userToken)
    }
}