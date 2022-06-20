package com.khue.joliecafejp.di

import android.content.Context
import com.khue.joliecafejp.data.repository.DataStoreOperationsImpl
import com.khue.joliecafejp.data.repository.Repository
import com.khue.joliecafejp.domain.repository.DataStoreOperations
import com.khue.joliecafejp.domain.use_cases.ApiUseCases
import com.khue.joliecafejp.domain.use_cases.DataStoreUseCases
import com.khue.joliecafejp.domain.use_cases.ValidationUseCases
import com.khue.joliecafejp.domain.use_cases.api.*
import com.khue.joliecafejp.domain.use_cases.data_store.ReadUserTokenUseCase
import com.khue.joliecafejp.domain.use_cases.data_store.SaveUserTokenUseCase
import com.khue.joliecafejp.domain.use_cases.validation_form.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDataStoreOperations(
        @ApplicationContext
        context: Context
    ): DataStoreOperations {
        return DataStoreOperationsImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideValidationUseCase(): ValidationUseCases {
        return ValidationUseCases(
            validationUserNameUseCase = ValidateUserNameUseCase(),
            validationEmailUseCaseUseCase = ValidateEmailUseCase(),
            validationPasswordUseCase = ValidatePasswordUseCase(),
            validationConfirmPasswordUseCase = ValidateConfirmPasswordUseCase(),
            validateUserPhoneNumberUseCase = ValidateUserPhoneNumberUseCase(),
            validateAddressUseCase = ValidateAddressUseCase()
        )
    }

    @Provides
    @Singleton
    fun provideDataStoreUseCase(repository: Repository): DataStoreUseCases {
        return DataStoreUseCases(
            saveUserTokenUseCase = SaveUserTokenUseCase(repository = repository),
            readUserTokenUseCase = ReadUserTokenUseCase(repository = repository)
        )
    }

    @Provides
    @Singleton
    fun provideApiUseCase(repository: Repository): ApiUseCases {
        return ApiUseCases(
            createUserUseCase = CreateUserUseCase(repository = repository),
            userLoginUseCase = UserLoginUseCase(repository = repository),
            getUserInfosUseCase = GetUserInfosUseCase(repository = repository),
            updateUserInfosUseCase = UpdateUserInfosUseCase(repository = repository),
            getProductsUseCase = GetProductsUseCase(repository = repository),
            getProductDetailUseCase = GetProductDetailUseCase(repository = repository),
            getUserFavoriteProductsUseCase = GetUserFavoriteProductsUseCase(repository = repository),
            getUserFavoriteProductsIdUseCase = GetUserFavoriteProductsIdUseCase(repository = repository),
            addUserFavoriteProductUseCase = AddUserFavoriteProductUseCase(repository = repository),
            removeUserFavProduct = RemoveUserFavProductUseCase(repository = repository),
            removeUserFavProductByProductId = RemoveUserFavProductByProductIdUseCase(repository = repository),
            getAddresses = GetAddressesUseCase(repository = repository),
            addNewAddressUseCase = AddNewAddressUseCase(repository = repository),
            addNewDefaultAddressUseCase = AddNewDefaultAddressUseCase(repository = repository),
            updateAddressUseCase = UpdateAddressUseCase(repository = repository),
            deleteAddressUseCase = DeleteAddressUseCase(repository = repository),
            addProductToCartUseCase = AddProductToCartUseCase(repository = repository),
            getCartItemsUseCase = GetCartItemsUseCase(repository = repository),
            getAdminNotificationForUserUseCase = GetAdminNotificationForUserUseCase(repository = repository),
            getUserBillsUseCase = GetUserBillsUseCase(repository = repository),
            reviewBillUseCase = ReviewBillUseCase(repository = repository),
            getProductCommentUseCase = GetProductCommentUseCase(repository = repository),
        )
    }
}