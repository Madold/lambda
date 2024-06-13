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
import androidx.navigation.NavHostController
import org.jetbrains.compose.resources.painterResource


@Composable
fun Drawer(
    drawerWidth: Dp = 200.dp,
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    
    var selectedOption: DrawerOptions by remember {
        mutableStateOf(DrawerOptions.UsersView)
    }
    
    Surface(
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier.width(drawerWidth)
                .fillMaxHeight()
    ) {
        Column(
            verticalArrangement = Arrangement.Top
        ) {

            Logo()

            DrawerOptions.entries.forEach { drawerOption ->
                DrawerItem(
                    drawerOption = drawerOption,
                    isSelected = drawerOption == selectedOption,
                    onClick = {
                        selectedOption = it
                        navController.navigate(drawerOption.label) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
    
    
}

@Composable
fun DrawerItem(
    drawerOption: DrawerOptions,
    isSelected: Boolean = false,
    onClick: (DrawerOptions) -> Unit,
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