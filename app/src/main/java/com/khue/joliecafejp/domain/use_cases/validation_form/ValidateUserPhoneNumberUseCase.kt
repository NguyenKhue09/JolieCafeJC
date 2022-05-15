package com.khue.joliecafejp.domain.use_cases.validation_form

import com.khue.joliecafejp.domain.model.ValidationResult

class ValidateUserPhoneNumberUseCase {

    fun execute(userPhoneNumber: String): ValidationResult {
        if(userPhoneNumber.isBlank()) {
            return ValidationResult(
                successful = false,
                errorMessage = "The phone number can't be blank"
            )
        }

        val rexPhoneNumber = """(84|0[3|5|7|8|9])+([0-9]{8})\b""".toRegex()

        if(!rexPhoneNumber.matches(userPhoneNumber)) {
            return ValidationResult(
                successful = false,
                errorMessage = "This is a valid phone number"
            )
        }

        return ValidationResult(
            successful = true
        )
    }
}