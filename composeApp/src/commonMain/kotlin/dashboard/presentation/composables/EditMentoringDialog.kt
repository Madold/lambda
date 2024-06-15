@file:OptIn(ExperimentalMaterial3Api::class)

package dashboard.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dashboard.domain.model.Mentoring
import dashboard.presentation.DashboardEvent
import dashboard.presentation.utils.TextFormattingUtils
import dashboard.presentation.utils.TextValidationUtils

@Composable
fun EditMentoringDialog(
    mentoring: Mentoring,
    onEvent: (DashboardEvent) -> Unit,
    onDissmissRequest: () -> Unit
) {
   
    var mentoringToSave by remember {
        mutableStateOf(mentoring)
    }
    
    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = System.currentTimeMillis(),
        initialDisplayMode = DisplayMode.Input
    )
    
    LaunchedEffect(key1 = datePickerState.selectedDateMillis) {
        datePickerState.selectedDateMillis?.let {
            mentoringToSave = mentoringToSave.copy(
                date = TextFormattingUtils.formatDateFromTimestamp(it)
            )
        }
    }
    
    Dialog(
        onDismissRequest = onDissmissRequest
    ) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                
                Text(
                    text = "Editar mentoría",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                
                DatePicker(
                    state = datePickerState
                )
                
                TextField(
                    value = mentoringToSave.duration.toString(),
                    onValueChange = {
                        if (TextValidationUtils.isPositiveLong(it)) {
                            mentoringToSave = mentoringToSave.copy(
                                duration = it.toLong()
                            )
                        }
                    },
                    singleLine = true,
                    label = {
                        Text("Duración (s)")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                TextField(
                    value = mentoringToSave.price.toString(),
                    onValueChange = {
                        if (TextValidationUtils.isPositiveFloat(it)) {
                            mentoringToSave = mentoringToSave.copy(
                                price = it.toDouble()
                            )
                        }
                    },
                    singleLine = true,
                    label = {
                        Text("Precio $")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                TextField(
                    value = mentoringToSave.totalRevenue.toString(),
                    onValueChange = {
                        if (TextValidationUtils.isPositiveFloat(it)) {
                            mentoringToSave = mentoringToSave.copy(
                                totalRevenue = it.toDouble()
                            )
                        }
                    },
                    singleLine = true,
                    label = {
                        Text("Recaudo total $")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Row(
                  horizontalArrangement = Arrangement.spacedBy(8.dp)  
                ) {
                    
                    OutlinedButton(
                        onClick = { onDissmissRequest() }
                    ) {
                        Text("Cancelar")
                    }
                    
                    Button(
                        onClick = {
                            onEvent(DashboardEvent.DeleteMentoringbyId(mentoringToSave.id))
                            onDissmissRequest()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text("Eliminar", color = Color.White)
                    }
                    
                    Button(
                        onClick = {
                            onEvent(DashboardEvent.UpdateMentoring(mentoringToSave))
                        }
                    ) {
                        Text("Guardar cambios")
                    }
                    
                }
                
            }
        }
    }
    
}