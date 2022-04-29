package com.khue.joliecafejp.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreOperations {
    suspend fun saveUserToken(userToken: String)
    fun readUserToken(): Flow<String>
}