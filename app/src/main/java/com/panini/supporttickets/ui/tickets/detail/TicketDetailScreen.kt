package com.panini.supporttickets.ui.tickets.detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.supporttickets.core.UiState
import com.panini.supporttickets.domain.model.Ticket
import com.panini.supporttickets.domain.model.TicketPriority
import com.panini.supporttickets.domain.model.TicketStatus
import com.panini.supporttickets.flags.FeatureFlags
import com.panini.supporttickets.ui.components.OptionSelector

@Composable
fun TicketDetailScreen(
    ticketId: String,
    onBack: () -> Unit,
    viewModel: TicketDetailViewModel = viewModel(factory = TicketDetailViewModel.factory(ticketId))
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            OutlinedButton(onClick = onBack) {
                Text("Volver")
            }
            when (val current = state) {
                UiState.Loading -> CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp)
                )
                is UiState.Error -> Text(
                    text = current.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
                is UiState.Success -> TicketDetailContent(
                    ticket = current.data,
                    onStatusSelected = viewModel::updateStatus,
                    onPrioritySelected = viewModel::updatePriority
                )
            }
        }
    }
}

@Composable
private fun TicketDetailContent(
    ticket: Ticket,
    onStatusSelected: (TicketStatus) -> Unit,
    onPrioritySelected: (TicketPriority) -> Unit
) {
    Text(
        text = ticket.title,
        style = MaterialTheme.typography.headlineSmall,
        modifier = Modifier.padding(top = 16.dp)
    )
    Text(
        text = ticket.description,
        style = MaterialTheme.typography.bodyLarge,
        modifier = Modifier.padding(top = 10.dp)
    )
    DetailRow("ID del ticket", ticket.id)
    DetailRow("Proveedor", ticket.supplier)
    DetailRow("Región", ticket.distributionRegion)
    DetailRow("Categoría", ticket.category.label)
    DetailRow("Creado", ticket.createdAt)
    DetailRow("Estado actual", ticket.status.label)
    DetailRow("Prioridad actual", ticket.priority.label)

    OptionSelector(
        title = "Actualizar estado",
        options = TicketStatus.entries,
        selectedOption = ticket.status,
        label = { it.label },
        onOptionSelected = onStatusSelected,
        modifier = Modifier.padding(top = 24.dp)
    )

    if (FeatureFlags.priorityUpdateEnabled) {
        OptionSelector(
            title = "Actualizar prioridad",
            options = TicketPriority.entries,
            selectedOption = ticket.priority,
            label = { it.label },
            onOptionSelected = onPrioritySelected,
            modifier = Modifier.padding(top = 20.dp)
        )
    } else {
        Text(
            text = "Las actualizaciones de prioridad están deshabilitadas.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 20.dp)
        )
    }
}

@Composable
private fun DetailRow(label: String, value: String) {
    Column(modifier = Modifier.padding(top = 10.dp)) {
        Text(text = label, style = MaterialTheme.typography.labelLarge)
        Text(text = value, style = MaterialTheme.typography.bodyMedium)
    }
}
