package dashboard.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import core.presentation.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dashboard.presentation.DashboardEvent
import dashboard.presentation.DashboardState

@Composable
fun AddUserDialog(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = onDismissRequest,
    ) {
        Card(modifier) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Text(
                    text = "Registrar usuario",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                TextField(
                    value = state.userId,
                    onValueChange = { onEvent(DashboardEvent.ChangeUserId(it)) },
                    placeholder = {
                        Text("ID del usuario")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )

                TextField(
                    value = state.userName,
                    onValueChange = { onEvent(DashboardEvent.ChangeUserName(it)) },
                    placeholder = {
                        Text("Nombre")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )

                TextField(
                    value = state.userLastName,
                    onValueChange = { onEvent(DashboardEvent.ChangeUserLastName(it)) },
                    placeholder = {
                        Text("Apellido")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )

                TextField(
                    value = state.userEmail,
                    onValueChange = { onEvent(DashboardEvent.ChangeUserEmail(it)) },
                    placeholder = {
                        Text("Email")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )


                TextField(
                    value = state.userRating.toString(),
                    onValueChange = { onEvent(DashboardEvent.ChangeUserRating(it.toFloat())) },
                    placeholder = {
                        Text("Valoración")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )

                Box(
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Button(onClick = { onDismissRequest() }) {
                            Text("Cancelar")
                        }

                        OutlinedButton(onClick = {
                            onEvent(DashboardEvent.SaveUser)
                            onDismissRequest()
                        }) {
                            Text("Guardar usuario")
                        }

                    }
                }

            }
        }
    }

}
