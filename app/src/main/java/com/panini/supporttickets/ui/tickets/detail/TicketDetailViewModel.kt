package com.panini.supporttickets.ui.tickets.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.panini.supporttickets.core.AppContainer
import com.panini.supporttickets.core.UiState
import com.panini.supporttickets.data.repository.TicketRepository
import com.panini.supporttickets.domain.model.Ticket
import com.panini.supporttickets.domain.model.TicketPriority
import com.panini.supporttickets.domain.model.TicketStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TicketDetailViewModel(
    private val ticketId: String,
    private val repository: TicketRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState<Ticket>>(UiState.Loading)
    val uiState: StateFlow<UiState<Ticket>> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeTicket(ticketId).collect { ticket ->
                _uiState.value = ticket?.let { UiState.Success(it) }
                    ?: UiState.Error("Ticket was not found.")
            }
        }
    }

    fun updateStatus(status: TicketStatus) {
        viewModelScope.launch {
            repository.updateStatus(ticketId, status)
        }
    }

    fun updatePriority(priority: TicketPriority) {
        viewModelScope.launch {
            repository.updatePriority(ticketId, priority)
        }
    }

    companion object {
        fun factory(ticketId: String): ViewModelProvider.Factory =
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T =
                    TicketDetailViewModel(ticketId, AppContainer.ticketRepository) as T
            }
    }
}
