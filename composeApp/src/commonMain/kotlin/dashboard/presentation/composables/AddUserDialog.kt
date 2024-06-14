package dashboard.presentation.composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import core.presentation.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import dashboard.presentation.DashboardEvent
import dashboard.presentation.DashboardState

@Composable
fun AddUserDialog(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = { onEvent(DashboardEvent.ChangeAddUserDialogVisibility(false)) },
        properties = DialogProperties(

        )
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
                    text = "Registrar usuario",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                TextField(
                    value = state.userId,
                    onValueChange = { onEvent(DashboardEvent.ChangeUserId(it)) },
                    placeholder = {
                        Text("ID del usuario")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    isError = state.idError != null
                )

                AnimatedVisibility(visible = state.idError != null) {
                    Text(state.idError ?: "", color = MaterialTheme.colorScheme.error)
                }

                TextField(
                    value = state.userName,
                    onValueChange = { onEvent(DashboardEvent.ChangeUserName(it)) },
                    placeholder = {
                        Text("Nombre")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    isError = state.nameError != null
                )

                AnimatedVisibility(visible = state.nameError != null) {
                    Text(state.nameError ?: "", color = MaterialTheme.colorScheme.error)
                }

                TextField(
                    value = state.userLastName,
                    onValueChange = { onEvent(DashboardEvent.ChangeUserLastName(it)) },
                    placeholder = {
                        Text("Apellido")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    isError = state.lastNameError != null
                )

                AnimatedVisibility(visible = state.lastNameError != null) {
                    Text(state.lastNameError ?: "", color = MaterialTheme.colorScheme.error)
                }

                TextField(
                    value = state.userEmail,
                    onValueChange = { onEvent(DashboardEvent.ChangeUserEmail(it)) },
                    placeholder = {
                        Text("Email")
                    },
                    modifier = Modifier.fillMaxWidth(0.7f),
                    isError = state.emailError != null
                )

                AnimatedVisibility(visible = state.emailError != null) {
                    Text(state.emailError ?: "", color = MaterialTheme.colorScheme.error)
                }


                RatingField(
                    value = state.userRating.toString(),
                    onValueChange = { onEvent(DashboardEvent.ChangeUserRating(it.toFloat())) },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )

                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {

                        Button(onClick = { onEvent(DashboardEvent.ChangeAddUserDialogVisibility(false)) }) {
                            Text("Cancelar")
                        }

                        OutlinedButton(onClick = {
                            onEvent(DashboardEvent.SaveUser)
                        }) {
                            Text("Guardar usuario")
                        }
                    }
                }
            }
        }
    }

}
