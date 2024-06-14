package dashboard.presentation.utils

object TextValidationUtils {
    
    private val POSITIVE_FLOAT_REGEX = Regex("^[+]?([0-9]*[.])?[0-9]+$")
    
    fun isPositiveFloat(value: String): Boolean {
        return value.matches(POSITIVE_FLOAT_REGEX)
    }
    
}


