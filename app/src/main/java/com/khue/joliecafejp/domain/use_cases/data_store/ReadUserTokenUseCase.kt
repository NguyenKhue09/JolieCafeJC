package com.khue.joliecafejp.domain.use_cases.data_store

import com.khue.joliecafejp.data.repository.Repository
import kotlinx.coroutines.flow.Flow

class ReadUserTokenUseCase(
    private val repository: Repository
){
    operator fun invoke(): Flow<String> {
        return repository.readUserToken()
    }
}