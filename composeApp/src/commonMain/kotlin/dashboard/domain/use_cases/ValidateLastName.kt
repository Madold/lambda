package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateLastName {
    operator fun invoke(lastName: String): ValidationResult {
        
        if (lastName.isBlank()) return ValidationResult(
            success = false,
            errorMessage = "El apellido no puede ser vacío"
        )
        
        return ValidationResult(success = true)
    }
}
