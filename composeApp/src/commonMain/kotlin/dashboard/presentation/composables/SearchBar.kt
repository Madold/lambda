package dashboard.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.ic_clear
import lambda.composeapp.generated.resources.ic_search
import org.jetbrains.compose.resources.painterResource

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit,
    onClear: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text("ID del usuario ")
        },
        modifier = modifier
            .scale(0.9f),
        shape = RoundedCornerShape(size = 20.dp),
        trailingIcon = {
            IconButton(onClick = { onSearch() }, enabled = value.isNotBlank()) {
                Icon(
                    painter = painterResource(Res.drawable.ic_search),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        leadingIcon = {
            AnimatedVisibility(
                visible = value.isNotBlank(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                IconButton(onClick = { onClear() }) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_clear),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        },
        enabled = enabled
    )
    
}