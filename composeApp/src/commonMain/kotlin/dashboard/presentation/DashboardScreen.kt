@file:OptIn(ExperimentalMaterial3Api::class)

package dashboard.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dashboard.presentation.composables.Drawer
import dashboard.presentation.composables.Logo
import dashboard.presentation.composables.SearchBar
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.ic_filter
import org.jetbrains.compose.resources.painterResource

@Composable
fun DashboardScreen(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        value = state.usersQuery,
                        onValueChange = { onEvent(DashboardEvent.ChangeUserQuery(it)) },
                        modifier = Modifier
                            .defaultMinSize(minWidth = 300.dp)
                            .fillMaxWidth(0.6f)
                    )
                },
                navigationIcon = {
                    Logo()
                },
                modifier = Modifier.padding(horizontal = 16.dp),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_filter),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Row(
          modifier = Modifier
              .padding(innerPadding)
              .fillMaxSize()  
        ) {
            Drawer()
        }
    }
}