@file:OptIn(ExperimentalMaterial3Api::class)

package dashboard.presentation.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dashboard.domain.model.User
import dashboard.presentation.DashboardEvent
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.empty_ilustration
import lambda.composeapp.generated.resources.ic_filter
import org.jetbrains.compose.resources.painterResource

@Composable
fun UsersListView(
    query: String,
    onQueryChange: (String) -> Unit,
    users: List<User>,
    modifier: Modifier = Modifier,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        value = query,
                        onValueChange = onQueryChange,
                        modifier = Modifier
                            .defaultMinSize(minWidth = 300.dp)
                            .fillMaxWidth(0.6f)
                    )
                },
                actions = {
                    IconButton(onClick = {

                    }) {
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
        AnimatedContent(
            targetState = users.isEmpty(),
            modifier = modifier.padding(innerPadding)
        ) { isUsersListEmpty ->
            if (isUsersListEmpty) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Image(
                        painterResource(Res.drawable.empty_ilustration),
                        contentDescription = null
                    )
                }
            } else {
                LazyColumn {
                    items(users, key = { it.id }) { user ->
                        //TODO Create user item
                    }
                }
            }

        }
    }


}