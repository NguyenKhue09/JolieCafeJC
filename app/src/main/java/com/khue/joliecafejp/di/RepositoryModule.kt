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
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateConfirmPasswordUseCase
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateEmailUseCase
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidatePasswordUseCase
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateUserNameUseCase
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
            validationUserNameUseCaseUseCase = ValidateUserNameUseCase(),
            validationEmailUseCaseUseCase = ValidateEmailUseCase(),
            validationPasswordUseCase = ValidatePasswordUseCase(),
            validationConfirmPasswordUseCase = ValidateConfirmPasswordUseCase()
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
            getProductsUseCase = GetProductsUseCase(repository = repository),
            getProductDetailUseCase = GetProductDetailUseCase(repository = repository),
            getUserFavoriteProductsUseCase = GetUserFavoriteProductsUseCase(repository = repository),
            removeUserFavProduct = RemoveUserFavProductUseCase(repository = repository)
        )
    }
}