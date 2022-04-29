package com.khue.joliecafejp.domain.use_cases

import com.khue.joliecafejp.domain.use_cases.api.CreateUserUseCase
import com.khue.joliecafejp.domain.use_cases.api.GetProductsUseCase

data class ApiUseCases(
    val createUserUseCase: CreateUserUseCase,
    val getProductsUseCase: GetProductsUseCase
)
