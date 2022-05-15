package com.khue.joliecafejp.domain.use_cases

import com.khue.joliecafejp.domain.use_cases.validation_form.*

data class ValidationUseCases(
    val validationUserNameUseCaseUseCase: ValidateUserNameUseCase,
    val validationEmailUseCaseUseCase: ValidateEmailUseCase,
    val validationPasswordUseCase: ValidatePasswordUseCase,
    val validationConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    val validateUserPhoneNumberUseCase: ValidateUserPhoneNumberUseCase
)
