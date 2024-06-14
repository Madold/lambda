package dashboard.presentation.composables

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import core.presentation.TextField

@Composable
fun RatingField(
    value: String,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    
    fun handleRatingChange(newValue: String) {
        
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