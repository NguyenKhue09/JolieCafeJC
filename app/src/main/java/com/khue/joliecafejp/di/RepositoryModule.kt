package com.khue.joliecafejp.di

import com.khue.joliecafejp.domain.use_cases.ValidationUseCases
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateConfirmPassword
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateEmail
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidatePassword
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateUserName
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideValidationUseCase(): ValidationUseCases {
        return ValidationUseCases(
            validationUserNameUseCase = ValidateUserName(),
            validationEmailUseCase = ValidateEmail(),
            validationPassword = ValidatePassword(),
            validationConfirmPassword = ValidateConfirmPassword()
        )
    }
}