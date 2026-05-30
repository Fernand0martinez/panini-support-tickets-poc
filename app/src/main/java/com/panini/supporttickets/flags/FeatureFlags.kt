package com.panini.supporttickets.flags

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object FeatureFlags {
    var ticketCreationEnabled by mutableStateOf(true)
        private set

    var priorityUpdateEnabled by mutableStateOf(true)
        private set

    fun updateTicketCreationEnabled(enabled: Boolean) {
        ticketCreationEnabled = enabled
    }

    fun updatePriorityUpdateEnabled(enabled: Boolean) {
        priorityUpdateEnabled = enabled
    }
}
