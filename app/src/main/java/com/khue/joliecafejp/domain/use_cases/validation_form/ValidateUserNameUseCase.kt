package com.khue.joliecafejp.domain.use_cases.validation_form

import com.khue.joliecafejp.domain.model.ValidationResult

class ValidateUserNameUseCase {

    fun execute(userName: String): ValidationResult {
        if(userName.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The username can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}