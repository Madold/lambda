package dashboard.presentation.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.presentation.TextField
import dashboard.domain.model.Donation
import dashboard.presentation.DashboardEvent
import dashboard.presentation.utils.TextValidationUtils

@Composable
fun EditDonationDialog(
    donation: Donation,
    onEvent: (DashboardEvent) -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    
    var donationToSave by rememberSaveable {
        mutableStateOf(donation)    
    }
    
    Dialog(
        onDismissRequest = onDismissRequest
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
                    text = "Editar donación",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
                )
                
                TextField(
                    value = donationToSave.amount.toString(),
                    onValueChange = {
                        if (TextValidationUtils.isPositiveFloat(it)) {
                            donationToSave = donationToSave.copy(
                                amount = it.toDouble()
                            )
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
                        onClick = onDismissRequest
                    ) {
                        Text("Cancelar")
                    }
                    
                    Button(
                        onClick = {
                            onEvent(DashboardEvent.DeleteDonationByUserId(donationToSave.userId))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text("Eliminar", color = Color.White)
                    }

                    Button(
                        onClick = {
                            onEvent(DashboardEvent.UpdateDonation(donationToSave))
                            onDismissRequest()
                        }
                    ) {
                        Text("Guardar cambios")
                    }
                }
            }
        }
    }
    
}
