package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateMount {

    operator fun invoke(mount: Double): ValidationResult {

        if (mount == 0.0) {
            return ValidationResult(
                success = false,
                errorMessage = "El monto no puede ser 0"
            )
        }

        return ValidationResult(
            success = true
        )

    }

}