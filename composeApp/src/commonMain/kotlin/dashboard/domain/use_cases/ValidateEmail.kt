package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateEmail {
    
    companion object {
        private val EMAIL_REGEX = Regex("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    }
    
    operator fun invoke(email: String): ValidationResult {

        if (!email.matches(EMAIL_REGEX)) {
            return ValidationResult(
                success = false,
                errorMessage = "El email no es válido"
            )
        }
    
        return ValidationResult(true)
    }
}
