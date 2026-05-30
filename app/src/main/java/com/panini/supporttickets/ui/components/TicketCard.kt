package com.panini.supporttickets.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.panini.supporttickets.domain.model.Ticket

@Composable
fun TicketCard(
    ticket: Ticket,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = ticket.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = "${ticket.supplier} · ${ticket.distributionRegion}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Row(modifier = Modifier.padding(top = 10.dp)) {
                Text(text = "Prioridad: ${ticket.priority.label}", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Estado: ${ticket.status.label}", style = MaterialTheme.typography.labelMedium)
            }
            Row(modifier = Modifier.padding(top = 6.dp)) {
                Text(text = "Categoría: ${ticket.category.label}", style = MaterialTheme.typography.labelMedium)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = "Creado: ${ticket.createdAt}", style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}
