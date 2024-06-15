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
import core.presentation.TextField
import dashboard.domain.model.User
import dashboard.presentation.DashboardEvent

@Composable
fun EditUserDialog(
    user: User,
    onEvent: (DashboardEvent) -> Unit,
    onDissmissRequest: () -> Unit
) {
    
    var usertToSave by remember {
        mutableStateOf(user)
    }
    
    Dialog(onDismissRequest = onDissmissRequest) {
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
                    text = "Editar usuario",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                
                TextField(
                    value = usertToSave.name,
                    onValueChange = { usertToSave = usertToSave.copy(name = it)},
                    placeholder = {
                        Text("Nombre")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f),
                )
                
                TextField(
                    value = usertToSave.lastName,
                    onValueChange = { usertToSave = usertToSave.copy(lastName = it) },
                    placeholder = {
                        Text("Apellido")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
                
                TextField(
                    value = usertToSave.email,
                    onValueChange = { usertToSave = usertToSave.copy(email = it) },
                    placeholder = {
                        Text("Email")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
                
                RatingField(
                    value = usertToSave.rating.toString(),
                    onValueChange = { usertToSave = usertToSave.copy(rating = it.toFloat()) },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Button(onClick = { onDissmissRequest() } ) {
                            Text("Cancelar")
                        }

                        Button(
                            onClick = {
                                onEvent(DashboardEvent.DeleteUser(user.id))
                                onDissmissRequest()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Text("Eliminar", color = Color.White)
                        }
                        
                        OutlinedButton(onClick = {
                            onEvent(DashboardEvent.UpdateUser(usertToSave))
                            onDissmissRequest()
                        }) {
                            Text("Guardar cambios")
                        }
                    }
                }
            }
        }
    }
    
}
