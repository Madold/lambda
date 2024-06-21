package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateMentoringPrice {

    operator fun invoke(price: Double): ValidationResult {

        if (price == 0.0) {
            return ValidationResult(
                success = false,
                errorMessage = "El precio no puede ser 0"
            )
        }

        return ValidationResult(
            success = true
        )
    }

}