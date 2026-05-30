package com.panini.supporttickets.ui.tickets.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.panini.supporttickets.core.AppContainer
import com.panini.supporttickets.core.UiState
import com.panini.supporttickets.data.repository.TicketRepository
import com.panini.supporttickets.domain.model.TicketCategory
import com.panini.supporttickets.domain.model.TicketPriority
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateTicketViewModel(
    private val repository: TicketRepository
) : ViewModel() {
    private val _creationState = MutableStateFlow<UiState<Unit>?>(null)
    val creationState: StateFlow<UiState<Unit>?> = _creationState.asStateFlow()

    fun createTicket(
        title: String,
        description: String,
        supplier: String,
        category: TicketCategory,
        priority: TicketPriority,
        distributionRegion: String
    ) {
        if (title.isBlank() || description.isBlank() || supplier.isBlank() || distributionRegion.isBlank()) {
            _creationState.value = UiState.Error("Completa todos los campos obligatorios.")
            return
        }

        viewModelScope.launch {
            _creationState.value = UiState.Loading
            repository.createTicket(
                title = title,
                description = description,
                supplier = supplier,
                category = category,
                priority = priority,
                distributionRegion = distributionRegion
            )
            _creationState.value = UiState.Success(Unit)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                CreateTicketViewModel(AppContainer.ticketRepository) as T
        }
    }
}
