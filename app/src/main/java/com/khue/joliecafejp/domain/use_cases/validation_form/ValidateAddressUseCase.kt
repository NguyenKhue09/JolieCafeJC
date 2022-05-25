package com.khue.joliecafejp.domain.use_cases.validation_form

import com.khue.joliecafejp.domain.model.ValidationResult

class ValidateAddressUseCase {

    fun execute(address: String): ValidationResult {
        if(address.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The address can't be blank"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}