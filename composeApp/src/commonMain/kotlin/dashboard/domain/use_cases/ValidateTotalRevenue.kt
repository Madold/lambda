package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateTotalRevenue {

    operator fun invoke(totalRevenue: Double): ValidationResult {

        if (totalRevenue == 0.0) {
            return ValidationResult(
                success = false,
                errorMessage = "El recaudo total no puede ser 0"
            )
        }

        return ValidationResult(
            success = true
        )

    }

}