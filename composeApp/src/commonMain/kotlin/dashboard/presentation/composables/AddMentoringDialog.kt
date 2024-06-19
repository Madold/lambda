@file:OptIn(ExperimentalMaterial3Api::class)

package dashboard.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dashboard.presentation.DashboardEvent
import dashboard.presentation.DashboardState
import dashboard.presentation.utils.TextFormattingUtils
import core.presentation.TextField
import dashboard.presentation.utils.TextValidationUtils

@Composable
fun AddMentoringDialog(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Input
    )

    var isComboboxExpanded by rememberSaveable {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            onEvent(DashboardEvent.ChangeMentoringDate(TextFormattingUtils.formatDateFromTimestamp(it)))
        }
    }

    Dialog(
        onDismissRequest = { onEvent(DashboardEvent.ChangeAddMentoringDialogVisibility(false)) }
    ) {

        Card(modifier) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = "Registrar mentoría",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                DatePicker(
                    state = datePickerState
                )

                TextField(
                    value = state.mentoringDuration.toString(),
                    onValueChange = {
                        if (TextValidationUtils.isPositiveLong(it)) {
                            onEvent(DashboardEvent.ChangeMentoringDuration(it.toLong()))
                        }
                    },
                    singleLine = true,
                    label = {
                        Text("Duración (s)")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = state.mentoringPrice.toString(),
                    onValueChange = {
                        if (TextValidationUtils.isPositiveFloat(it)) {
                            onEvent(DashboardEvent.ChangeMentoringPrice(it.toDouble()))
                        }
                    },
                    singleLine = true,
                    label = {
                        Text("Precio $")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = state.mentoringTotalReveune.toString(),
                    onValueChange = {
                        if (TextValidationUtils.isPositiveFloat(it)) {
                            onEvent(DashboardEvent.ChangeMentoringTotalRevenue(it.toDouble()))
                        }
                    },
                    singleLine = true,
                    label = {
                        Text("Recaudo total $")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                ExposedDropdownMenuBox(
                    expanded = isComboboxExpanded,
                    onExpandedChange = { isComboboxExpanded = !isComboboxExpanded },
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextField(
                        value = state.mentoringUserId,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            IconButton(onClick = { isComboboxExpanded = true }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null,
                                    modifier = Modifier.rotate(if (isComboboxExpanded) 180f else 0f)
                                )
                            }
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth(),
                        label = {
                            Text("Id del usuario")
                        }
                    )

                    ExposedDropdownMenu(
                        expanded = isComboboxExpanded,
                        onDismissRequest = { isComboboxExpanded = false }
                    ) {
                        state.users.forEach {
                            DropdownMenuItem(
                                text = {
                                    Text("${it.id} ${it.name} ")
                                },
                                onClick = {
                                    onEvent(DashboardEvent.ChangeMentoringUserId(it.id))
                                    isComboboxExpanded = false
                                }
                            )
                        }
                    }

                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedButton(
                        onClick = { onEvent(DashboardEvent.ChangeAddMentoringDialogVisibility(false)) }
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            onEvent(DashboardEvent.SaveMentoring)
                        }
                    ) {
                        Text("Guardar tutoría")
                    }

                }

            }
        }
    }

}