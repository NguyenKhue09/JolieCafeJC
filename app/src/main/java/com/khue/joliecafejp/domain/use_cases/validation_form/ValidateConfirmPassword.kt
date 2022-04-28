package com.khue.joliecafejp.domain.use_cases.validation_form

import com.khue.joliecafejp.domain.model.ValidationResult


class ValidateConfirmPassword {

    fun execute(password: String, confirmPassword: String): ValidationResult {
        if (password != confirmPassword) {
            return ValidationResult(
                successful = false,
                errorMessage = "The passwords don't match"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}