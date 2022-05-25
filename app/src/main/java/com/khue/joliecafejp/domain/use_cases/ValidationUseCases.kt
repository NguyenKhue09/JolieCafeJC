package com.khue.joliecafejp.domain.use_cases

import com.khue.joliecafejp.domain.use_cases.validation_form.*

data class ValidationUseCases(
    val validationUserNameUseCase: ValidateUserNameUseCase,
    val validationEmailUseCaseUseCase: ValidateEmailUseCase,
    val validationPasswordUseCase: ValidatePasswordUseCase,
    val validationConfirmPasswordUseCase: ValidateConfirmPasswordUseCase,
    val validateUserPhoneNumberUseCase: ValidateUserPhoneNumberUseCase,
    val validateAddressUseCase: ValidateAddressUseCase
)
