package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateId {
    
    companion object {
        private const val ID_LENGTH = 10
    }
    
    operator fun invoke(id: String): ValidationResult {
        
        if (id.isBlank()) {
            return ValidationResult(
                success = false,
                errorMessage = "El id no puede ser vacío"
            )
        }
        
        if (id.length != ID_LENGTH) {
            return ValidationResult(
                success = false,
                errorMessage = "El id tiene que ser de 10 digitos"
            )
        }
        
        return ValidationResult(success = true)
    }
    
}
