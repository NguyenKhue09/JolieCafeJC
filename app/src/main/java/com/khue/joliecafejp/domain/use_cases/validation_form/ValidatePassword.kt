package com.khue.joliecafejp.domain.use_cases.validation_form

import com.khue.joliecafejp.domain.model.ValidationResult

class ValidatePassword {

    fun execute(password: String): ValidationResult {
        if (password.length < 8) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to consist of at least 8 characters "
            )
        }

        val containsLetterAnDigits = password.any { it.isDigit() } && password.any { it.isLetter() }

        if (!containsLetterAnDigits) {
            return ValidationResult(
                successful = false,
                errorMessage = "The password needs to contain at least one letter and digit"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}