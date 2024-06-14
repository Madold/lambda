package dashboard.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UsersListHeader(
    modifier: Modifier = Modifier,
    label: @Composable () -> Unit,
) {
    
    Box(
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer)
            .padding(
                horizontal = 4.dp,
                vertical = 8.dp
            ),
        contentAlignment = Alignment.Center
    ) {
        label()
    }
    
}