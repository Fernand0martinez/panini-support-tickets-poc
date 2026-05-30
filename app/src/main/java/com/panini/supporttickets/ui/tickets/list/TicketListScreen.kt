package com.panini.supporttickets.ui.tickets.list

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.supporttickets.domain.model.TicketCategory
import com.panini.supporttickets.flags.FeatureFlags
import com.panini.supporttickets.ui.components.TicketCard

@Composable
fun TicketListScreen(
    onTicketClick: (String) -> Unit,
    onCreateClick: () -> Unit,
    viewModel: TicketListViewModel = viewModel(factory = TicketListViewModel.Factory)
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val ticketCreationEnabled = FeatureFlags.ticketCreationEnabled

    Scaffold(
        floatingActionButton = {
            if (ticketCreationEnabled) {
                ExtendedFloatingActionButton(
                    onClick = onCreateClick
                ) {
                    Text("Crear ticket")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp, vertical = 18.dp)
        ) {
            Text(text = "Tickets de soporte", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "Incidentes de proveedores, inventario, distribución y logística.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp, bottom = 14.dp)
            )

            FeatureFlagControls(modifier = Modifier.padding(bottom = 14.dp))

            CategoryFilter(
                selectedCategory = state.selectedCategory,
                onCategorySelected = viewModel::selectCategory
            )

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 32.dp)
                )
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(top = 12.dp, bottom = 96.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(state.tickets, key = { it.id }) { ticket ->
                        TicketCard(
                            ticket = ticket,
                            onClick = { onTicketClick(ticket.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FeatureFlagControls(modifier: Modifier = Modifier) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = "Control de funcionalidades",
            style = MaterialTheme.typography.labelLarge
        )
        FeatureFlagSwitch(
            title = "Creación de tickets",
            checked = FeatureFlags.ticketCreationEnabled,
            onCheckedChange = FeatureFlags::updateTicketCreationEnabled
        )
        FeatureFlagSwitch(
            title = "Actualización de prioridad",
            checked = FeatureFlags.priorityUpdateEnabled,
            onCheckedChange = FeatureFlags::updatePriorityUpdateEnabled
        )
    }
}

@Composable
private fun FeatureFlagSwitch(
    title: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyMedium)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}

@Composable
private fun CategoryFilter(
    selectedCategory: TicketCategory?,
    onCategorySelected: (TicketCategory?) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
    ) {
        FilterChip(
            selected = selectedCategory == null,
            onClick = { onCategorySelected(null) },
            label = { Text("Todos") }
        )
        TicketCategory.entries.forEach { category ->
            FilterChip(
                selected = selectedCategory == category,
                onClick = { onCategorySelected(category) },
                label = { Text(category.label) }
            )
        }
    }
}
