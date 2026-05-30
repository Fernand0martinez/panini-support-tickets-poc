package com.panini.supporttickets.ui.tickets.create

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.panini.supporttickets.core.UiState
import com.panini.supporttickets.domain.model.TicketCategory
import com.panini.supporttickets.domain.model.TicketPriority
import com.panini.supporttickets.flags.FeatureFlags
import com.panini.supporttickets.ui.components.OptionSelector

@Composable
fun CreateTicketScreen(
    onBack: () -> Unit,
    onCreated: () -> Unit,
    viewModel: CreateTicketViewModel = viewModel(factory = CreateTicketViewModel.Factory)
) {
    val creationState by viewModel.creationState.collectAsStateWithLifecycle()
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var supplier by remember { mutableStateOf("") }
    var region by remember { mutableStateOf("") }
    var category by remember { mutableStateOf(TicketCategory.INVENTORY) }
    var priority by remember { mutableStateOf(TicketPriority.HIGH) }

    LaunchedEffect(creationState) {
        if (creationState is UiState.Success) {
            onCreated()
        }
    }

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
            Text(
                text = "Crear ticket",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 16.dp)
            )

            if (!FeatureFlags.ticketCreationEnabled) {
                Text(
                    text = "La creación de tickets está deshabilitada.",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
                return@Column
            }

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Título") },
                placeholder = { Text("Ej. Faltan sobres de estampas en envío mayorista") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                placeholder = { Text("Describe el incidente, impacto y contexto operativo") },
                minLines = 3,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            OutlinedTextField(
                value = supplier,
                onValueChange = { supplier = it },
                label = { Text("Proveedor") },
                placeholder = { Text("Ej. Suministros Coleccionables Globales") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
            OutlinedTextField(
                value = region,
                onValueChange = { region = it },
                label = { Text("Región de distribución") },
                placeholder = { Text("Ej. Norteamérica") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )

            OptionSelector(
                title = "Categoría",
                options = TicketCategory.entries,
                selectedOption = category,
                label = { it.label },
                onOptionSelected = { category = it },
                modifier = Modifier.padding(top = 18.dp)
            )
            OptionSelector(
                title = "Prioridad",
                options = TicketPriority.entries,
                selectedOption = priority,
                label = { it.label },
                onOptionSelected = { priority = it },
                modifier = Modifier.padding(top = 18.dp)
            )

            Button(
                onClick = {
                    viewModel.createTicket(
                        title = title,
                        description = description,
                        supplier = supplier,
                        category = category,
                        priority = priority,
                        distributionRegion = region
                    )
                },
                enabled = creationState !is UiState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                Text("Enviar ticket")
            }

            when (val state = creationState) {
                is UiState.Error -> Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 12.dp)
                )
                UiState.Loading -> CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
                else -> Unit
            }
        }
    }
}
