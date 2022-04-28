package com.khue.joliecafejp.domain.use_cases

import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateConfirmPassword
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateEmail
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidatePassword
import com.khue.joliecafejp.domain.use_cases.validation_form.ValidateUserName

data class ValidationUseCases(
    val validationUserNameUseCase: ValidateUserName,
    val validationEmailUseCase: ValidateEmail,
    val validationPassword: ValidatePassword,
    val validationConfirmPassword: ValidateConfirmPassword
)
