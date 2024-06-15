@file:OptIn(ExperimentalMaterial3Api::class)

package dashboard.presentation.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dashboard.presentation.DashboardEvent
import dashboard.presentation.DashboardState
import dashboard.presentation.UsersListSortType
import lambda.composeapp.generated.resources.*
import lambda.composeapp.generated.resources.Res
import lambda.composeapp.generated.resources.empty_ilustration
import lambda.composeapp.generated.resources.ic_filter
import org.jetbrains.compose.resources.painterResource

@Composable
fun UsersListView(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState
) {

    var isDropDownMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    SearchBar(
                        value = state.usersQuery,
                        onValueChange = { onEvent(DashboardEvent.ChangeUserQuery(it)) },
                        onSearch = { onEvent(DashboardEvent.SearchUser) },
                        onClear = { onEvent(DashboardEvent.ClearUserQuery) },
                        modifier = Modifier
                            .defaultMinSize(minWidth = 300.dp)
                            .fillMaxWidth(0.6f),
                    )
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(DashboardEvent.ChangeAddUserDialogVisibility(true))
                    }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = null
                        )
                    }

                    IconButton(onClick = { isDropDownMenuVisible = true }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_filter),
                            contentDescription = null
                        )
                    }

                    DropdownMenu(
                        expanded = isDropDownMenuVisible,
                        onDismissRequest = { isDropDownMenuVisible = false }
                    ) {

                        DropdownMenuItem(
                            onClick = {

                                if (state.usersSortType == UsersListSortType.NameDesc) {
                                    onEvent(DashboardEvent.ChangeUserSortType(UsersListSortType.NameAsc))
                                    return@DropdownMenuItem
                                }

                                onEvent(DashboardEvent.ChangeUserSortType(UsersListSortType.NameDesc))

                            },
                            text = {
                                Text(text = if (state.usersSortType == UsersListSortType.NameDesc) "Nombre ascendente" else "Nombre descendente")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(if (state.usersSortType == UsersListSortType.NameDesc) Res.drawable.ic_za else Res.drawable.ic_az),
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            onClick = {
                                onEvent(DashboardEvent.ChangeUserSortType(UsersListSortType.Rating))
                            },
                            text = {
                                Text("Valoración")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_star),
                                    contentDescription = null
                                )
                            }
                        )

                    }

                }
            )
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = state.users.isEmpty(),
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
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            UsersListHeader(modifier = Modifier.weight(1f)) {
                                Text("id")
                            }

                            UsersListHeader(modifier = Modifier.weight(1f)) {
                                Text("Nombre")
                            }

                            UsersListHeader(modifier = Modifier.weight(1f)) {
                                Text("Apellido")
                            }

                            UsersListHeader(modifier = Modifier.weight(1.4f)) {
                                Text("Correo")
                            }

                            UsersListHeader(modifier = Modifier.weight(1f)) {
                                Text("Valoración")
                            }
                        }
                    }
                    itemsIndexed(
                        state.filteredUsers.ifEmpty { state.users },
                        key = { _, item -> item.id }
                    ) { index, user ->
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            val isOddIndex = index % 2 == 0
                            val backgroundColor =
                                if (isOddIndex) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.secondaryContainer

                            UserListEntry(
                                modifier = Modifier.weight(1f),
                                backgroundColor = backgroundColor
                            ) {
                                Text(user.id)
                            }

                            UserListEntry(
                                modifier = Modifier.weight(1f),
                                backgroundColor = backgroundColor
                            ) {
                                Text(user.name)
                            }

                            UserListEntry(
                                modifier = Modifier.weight(1f),
                                backgroundColor = backgroundColor
                            ) {
                                Text(user.lastName)
                            }

                            UserListEntry(
                                modifier = Modifier.weight(1.4f),
                                backgroundColor = backgroundColor
                            ) {
                                Text(user.email)
                            }

                            UserListEntry(
                                modifier = Modifier.weight(1f),
                                backgroundColor = backgroundColor
                            ) {
                                Text(user.rating.toString())
                            }
                        }
                    }
                }
            }
        }

        if (state.isAddUserDialogVisible) {
            AddUserDialog(
                state = state,
                onEvent = onEvent,
            )
        }

    }


}