package dashboard.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.presentation.TextField
import dashboard.presentation.DashboardEvent
import dashboard.presentation.DashboardState
import dashboard.presentation.utils.TextValidationUtils

@Composable
fun AddDonationDialog(
    state: DashboardState,
    onEvent: (DashboardEvent) -> Unit,
    modifier: Modifier = Modifier
) {

    Dialog(
        onDismissRequest = { onEvent(DashboardEvent.ChangeAddDonationDialogVisibility(false)) }
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
                    text = "Registrar donación",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )

                ComboBox(
                    value = state.donationUserId,
                    options = state.users.map { it.id },
                    label = {
                        Text("Id del usuario")
                    },
                    onOptionChange = {
                        onEvent(DashboardEvent.ChangeDonationUserId(it))
                    }
                )
                
                ComboBox(
                    value = state.donationMentoringId.toString(),
                    options = state.mentories.map { it.id.toString() },
                    label = {
                        Text("Id de la mentoría")
                    },
                    onOptionChange = {
                        onEvent(DashboardEvent.ChangeDonationMentoringId(it.toInt()))
                    }
                )
                
                TextField(
                    value = state.donationAmount.toString(),
                    onValueChange = {
                        if (TextValidationUtils.isPositiveFloat(it)) {
                            onEvent(DashboardEvent.ChangeDonationAmount(it.toDouble()))
                        }
                    },
                    singleLine = true,
                    label = {
                        Text("Monto")
                    },
                    modifier = Modifier.fillMaxWidth()
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedButton(
                        onClick = { onEvent(DashboardEvent.ChangeAddDonationDialogVisibility(false)) }
                    ) {
                        Text("Cancelar")
                    }

                    Button(
                        onClick = {
                            onEvent(DashboardEvent.SaveDonation)
                        }
                    ) {
                        Text("Guardar donación")
                    }

                }
                
            }
        }

    }

}
