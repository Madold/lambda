package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateUserId {

    operator fun invoke(userId: String): ValidationResult {

        if (userId.isEmpty()) {
            return ValidationResult(
                success = false,
                errorMessage = "Debes seleccionar un id de usuario"
            )
        }

        return ValidationResult(
            success = true
        )

    }

}