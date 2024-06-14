package dashboard.presentation.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.presentation.TextField
import dashboard.presentation.utils.TextValidationUtils

private const val MAX_RATING = 5.0f

@Composable
fun RatingField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    
    fun handleRatingChange(newValue: String) {
        if (TextValidationUtils.isPositiveFloat(newValue) && newValue.toFloat() <= MAX_RATING) {
            onValueChange(newValue)
        }
    }
    
    TextField(
        value = value,
        onValueChange = ::handleRatingChange,
        placeholder = {
            Text("Valoración")
        },
        modifier = modifier
    )
    
}