package com.khue.joliecafejp.domain.use_cases

import com.khue.joliecafejp.domain.use_cases.api.*

data class ApiUseCases(
    val createUserUseCase: CreateUserUseCase,
    val userLoginUseCase: UserLoginUseCase,
    val getUserInfosUseCase: GetUserInfosUseCase,
    val updateUserInfosUseCase: UpdateUserInfosUseCase,
    val getProductsUseCase: GetProductsUseCase,
    val getProductDetailUseCase: GetProductDetailUseCase,
    val getUserFavoriteProductsUseCase: GetUserFavoriteProductsUseCase,
    val getUserFavoriteProductsIdUseCase: GetUserFavoriteProductsIdUseCase,
    val addUserFavoriteProductUseCase: AddUserFavoriteProductUseCase,
    val removeUserFavProduct: RemoveUserFavProductUseCase,
    val removeUserFavProductByProductId: RemoveUserFavProductByProductIdUseCase,
    val getAddresses: GetAddressesUseCase,
    val updateAddressUseCase: UpdateAddressUseCase,
    val deleteAddressUseCase: DeleteAddressUseCase,
    val addNewAddressUseCase: AddNewAddressUseCase,
    val addNewDefaultAddressUseCase: AddNewDefaultAddressUseCase,
    val addProductToCartUseCase: AddProductToCartUseCase,
    val getCartItemsUseCase: GetCartItemsUseCase
)
