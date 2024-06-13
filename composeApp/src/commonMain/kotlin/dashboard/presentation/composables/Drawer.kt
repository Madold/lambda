package dashboard.presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import lambda.composeapp.generated.resources.*
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.ic_class_scene
import lambda.composeapp.generated.resources.ic_graduation_cap
import lambda.composeapp.generated.resources.ic_user
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

data class DrawerOption(
    val icon: DrawableResource,
    val label: String
)

@Composable
fun Drawer(
    drawerWidth: Dp = 200.dp,
    modifier: Modifier = Modifier
) {
    
    val drawerOptions = listOf(
        DrawerOption(
            icon = Res.drawable.ic_user,
            label = "Usuarios"
        ),
        DrawerOption(
            icon = Res.drawable.ic_graduation_cap,
            label = "Cursos",
        ),
        DrawerOption(
            icon = Res.drawable.ic_class_scene,
            label = "Tutorias"
        ),
        DrawerOption(
            icon = Res.drawable.ic_donation,
            label = "Donaciones"
        )
    )
    
    var selectedOption by remember {
        mutableStateOf(drawerOptions.first())
    }
    
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.width(200.dp)
                .fillMaxHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.Center
        ) {
            drawerOptions.forEach { drawerOption ->  
                DrawerItem(
                    drawerOption = drawerOption,
                    isSelected = drawerOption == selectedOption,
                    onClick = { selectedOption = it }
                )
            }
        }
    }
    
    
}

@Composable
fun DrawerItem(
    drawerOption: DrawerOption,
    isSelected: Boolean = false,
    onClick: (DrawerOption) -> Unit,
    modifier: Modifier = Modifier,
) {
    
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant 
    
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = { onClick(drawerOption) })
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(drawerOption.icon),
            contentDescription = null
        ) 
        Text(text = drawerOption.label)
    }
}