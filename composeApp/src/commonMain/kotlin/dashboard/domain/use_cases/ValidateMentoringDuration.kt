package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateMentoringDuration {

    operator fun invoke(duration: Long): ValidationResult {

        if (duration == 0L) {
            return ValidationResult(
                success = false,
                errorMessage = "La duración no puede ser 0"
            )
        }

        return ValidationResult(success = true)

    }

}
