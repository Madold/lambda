package dashboard.domain.use_cases

import dashboard.domain.utils.ValidationResult

class ValidateMentoringId {

    operator fun invoke(mentoringId: String): ValidationResult {

        if (mentoringId.isEmpty()) {
            return ValidationResult(
                success = false,
                errorMessage = "Debes seleccionar el id de una tutoría"
            )
        }

        return ValidationResult(
            success = true
        )

    }

}