@file:OptIn(ExperimentalMaterial3Api::class)

package dashboard.presentation.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import dashboard.presentation.DashboardEvent
import dashboard.presentation.DashboardState
import dashboard.presentation.DonationListSortType
import dashboard.presentation.UsersListSortType
import lambda.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun DonationsListView(
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
                        value = state.donationsQuery,
                        onValueChange = { onEvent(DashboardEvent.ChangeDonationsQuery(it)) },
                        onSearch = { onEvent(DashboardEvent.SearchDonations) },
                        onClear = { onEvent(DashboardEvent.ClearDonationsQuery) },
                        modifier = Modifier
                            .defaultMinSize(minWidth = 300.dp)
                            .fillMaxWidth(0.6f),
                    )
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(DashboardEvent.ChangeAddDonationDialogVisibility(true))
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

                                if (state.donationListSortType == DonationListSortType.MountDesc) {
                                    onEvent(DashboardEvent.ChangeDonationsSortType(DonationListSortType.MountAsc))
                                    return@DropdownMenuItem
                                }

                                onEvent(DashboardEvent.ChangeDonationsSortType(DonationListSortType.MountDesc))

                            },
                            text = {
                                Text(text = if (state.donationListSortType == DonationListSortType.MountDesc) "Monto ascendente" else "Monto descendente")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_money),
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
                                Text("ID del usuario")
                            }

                            UsersListHeader(modifier = Modifier.weight(1f)) {
                                Text("ID tutoría")
                            }

                            UsersListHeader(modifier = Modifier.weight(1f)) {
                                Text("Valor")
                            }
                        }
                    }
                    itemsIndexed(
                        state.filteredDonations.ifEmpty { state.donations },
                        key = { index, item -> item.mentoringId * index }
                    ) { index, donation ->

                        val interactionSource = remember { MutableInteractionSource() }
                        var isEditDonationDialogVisible by rememberSaveable { mutableStateOf(false) }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .indication(interactionSource, LocalIndication.current)
                                .pointerInput(true) {
                                    detectTapGestures(
                                        onLongPress = {
                                            isEditDonationDialogVisible = true
                                        },
                                        onPress = {
                                            val press = PressInteraction.Press(it)
                                            interactionSource.emit(press)
                                            tryAwaitRelease()
                                            interactionSource.emit(PressInteraction.Release(press))
                                        }
                                    )
                                }
                        ) {

                            val isOddIndex = index % 2 == 0
                            val backgroundColor =
                                if (isOddIndex) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.secondaryContainer

                            UserListEntry(
                                modifier = Modifier.weight(1f),
                                backgroundColor = backgroundColor
                            ) {
                                Text(donation.userId)
                            }

                            UserListEntry(
                                modifier = Modifier.weight(1f),
                                backgroundColor = backgroundColor
                            ) {
                                Text(donation.mentoringId.toString())
                            }

                            UserListEntry(
                                modifier = Modifier.weight(1f),
                                backgroundColor = backgroundColor
                            ) {
                                Text(donation.amount.toString())
                            }
                        }

                        if (isEditDonationDialogVisible) {
                            EditDonationDialog(
                                donation = donation,
                                onEvent = onEvent,
                                onDismissRequest = { isEditDonationDialogVisible = false }
                            )
                        }

                    }
                }
            }
        }

        if (state.isAddDonationDialogVisible) {
            AddDonationDialog(
                state = state,
                onEvent = onEvent
            )
        }

    }


}
    
