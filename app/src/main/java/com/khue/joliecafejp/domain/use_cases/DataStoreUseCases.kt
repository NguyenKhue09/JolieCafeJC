package com.khue.joliecafejp.domain.use_cases

import com.khue.joliecafejp.domain.use_cases.data_store.ReadUserTokenUseCase
import com.khue.joliecafejp.domain.use_cases.data_store.SaveUserTokenUseCase

data class DataStoreUseCases(
    val saveUserTokenUseCase: SaveUserTokenUseCase,
    val readUserTokenUseCase: ReadUserTokenUseCase
)
