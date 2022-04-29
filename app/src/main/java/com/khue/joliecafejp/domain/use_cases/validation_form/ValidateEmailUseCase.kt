package com.khue.joliecafejp.domain.use_cases.validation_form

import android.util.Patterns
import com.khue.joliecafejp.domain.model.ValidationResult

class ValidateEmailUseCase {

    fun execute(email: String): ValidationResult {
        if(email.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The email can't be blank"
            )
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                successful = false,
                errorMessage = "That's not a valid email"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}