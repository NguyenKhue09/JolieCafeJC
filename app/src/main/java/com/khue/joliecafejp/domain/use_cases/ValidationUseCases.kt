package com.khue.joliecafejp.domain.use_cases

import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateConfirmPasswordUseCase
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateEmailUseCase
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidatePasswordUseCase
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateUserNameUseCase

data class ValidationUseCases(
    val validationUserNameUseCaseUseCase: ValidateUserNameUseCase,
    val validationEmailUseCaseUseCase: ValidateEmailUseCase,
    val validationPasswordUseCase: ValidatePasswordUseCase,
    val validationConfirmPasswordUseCase: ValidateConfirmPasswordUseCase
)
