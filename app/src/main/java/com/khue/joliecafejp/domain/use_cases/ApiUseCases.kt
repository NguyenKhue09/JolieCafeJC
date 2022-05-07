package com.khue.joliecafejp.domain.use_cases

import com.khue.joliecafejp.domain.use_cases.api.*

data class ApiUseCases(
    val createUserUseCase: CreateUserUseCase,
    val userLoginUseCase: UserLoginUseCase,
    val getUserInfosUseCase: GetUserInfosUseCase,
    val getProductsUseCase: GetProductsUseCase,
    val getUserFavoriteProductsUseCase: GetUserFavoriteProductsUseCase
)
