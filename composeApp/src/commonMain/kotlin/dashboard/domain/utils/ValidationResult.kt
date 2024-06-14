package dashboard.domain.utils

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String? = null
)
