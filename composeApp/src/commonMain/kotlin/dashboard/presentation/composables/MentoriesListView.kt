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
import dashboard.presentation.DashboardEvent.*
import dashboard.presentation.DashboardState
import dashboard.presentation.MentoringListSortType
import lambda.composeapp.generated.resources.*
import org.jetbrains.compose.resources.painterResource

@Composable
fun MentoriesListView(
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
                        value = state.mentoriesQuery,
                        onValueChange = { onEvent(ChangeMentoriesQuery(it)) },
                        onSearch = { onEvent(SearchMentoring) },
                        onClear = { onEvent(ClearMentoringQuery) },
                        modifier = Modifier
                            .defaultMinSize(minWidth = 300.dp)
                            .fillMaxWidth(0.6f),
                    )
                },
                actions = {
                    IconButton(onClick = {
                        onEvent(ChangeAddMentoringDialogVisibility(true))
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

                                if (state.mentoringListSortType == MentoringListSortType.TotalReveuneDesc) {
                                    onEvent(ChangeMentoringSortType(MentoringListSortType.TotalReveuneAsc))
                                    return@DropdownMenuItem
                                }

                                onEvent(ChangeMentoringSortType(MentoringListSortType.TotalReveuneDesc))

                            },
                            text = {
                                Text(text = if (state.mentoringListSortType == MentoringListSortType.TotalReveuneDesc) "Total recaudado ascendente" else "Total recaudado descendente")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_money),
                                    contentDescription = null
                                )
                            }
                        )

                        DropdownMenuItem(
                            onClick = {
                                onEvent(ChangeMentoringSortType(MentoringListSortType.Duration))
                            },
                            text = {
                                Text("Duración")
                            },
                            leadingIcon = {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_clock),
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
            targetState = state.mentories.isEmpty(),
            modifier = modifier.padding(innerPadding)
        ) { isMentoriesListEmpty ->
            if (isMentoriesListEmpty) {
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
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    LazyColumn(modifier = Modifier.weight(1f)) {
                        item {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                UsersListHeader(modifier = Modifier.weight(1f)) {
                                    Text("Fecha")
                                }

                                UsersListHeader(modifier = Modifier.weight(1f)) {
                                    Text("Duración")
                                }

                                UsersListHeader(modifier = Modifier.weight(1f)) {
                                    Text("Valor")
                                }

                                UsersListHeader(modifier = Modifier.weight(1.4f)) {
                                    Text("Total recaudado")
                                }

                                UsersListHeader(modifier = Modifier.weight(1f)) {
                                    Text("id del usuario")
                                }
                            }
                        }
                        itemsIndexed(
                            state.filteredMentories.ifEmpty { state.mentories },
                            key = { _, item -> item.id }
                        ) { index, mentoring ->

                            val interactionSource = remember { MutableInteractionSource() }
                            var isEditMentoringDialogVisible by rememberSaveable { mutableStateOf(false) }

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .indication(interactionSource, LocalIndication.current)
                                    .pointerInput(true) {
                                        detectTapGestures(
                                            onLongPress = {
                                                isEditMentoringDialogVisible = true
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
                                    Text(mentoring.date)
                                }

                                UserListEntry(
                                    modifier = Modifier.weight(1f),
                                    backgroundColor = backgroundColor
                                ) {
                                    Text(mentoring.duration.toString())
                                }

                                UserListEntry(
                                    modifier = Modifier.weight(1f),
                                    backgroundColor = backgroundColor
                                ) {
                                    Text(mentoring.price.toString())
                                }

                                UserListEntry(
                                    modifier = Modifier.weight(1f),
                                    backgroundColor = backgroundColor
                                ) {
                                    Text(mentoring.totalRevenue.toString())
                                }

                                UserListEntry(
                                    modifier = Modifier.weight(1.4f),
                                    backgroundColor = backgroundColor
                                ) {
                                    Text(mentoring.userId)
                                }

                            }

                            if (isEditMentoringDialogVisible) {
                                EditMentoringDialog(
                                    mentoring = mentoring,
                                    onEvent = onEvent,
                                    onDissmissRequest = {
                                        isEditMentoringDialogVisible = false
                                    }
                                )
                            }

                        }
                    }

                    TotalRevenueChart(
                        mentories = state.mentories,
                        modifier = Modifier.weight(1f)
                    )
                }

            }
        }

        if (state.isAddMentoringDialogVisible) {
            AddMentoringDialog(
                state = state,
                onEvent = onEvent
            )
        }

    }

}