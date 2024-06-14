package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateName {
    operator fun invoke(name: String): ValidationResult {
        
        if (name.isBlank()) return ValidationResult(
            success = false,
            errorMessage = "El nombre no puede ser vacío"
        )
        
        return ValidationResult(success = true)
    }
}
