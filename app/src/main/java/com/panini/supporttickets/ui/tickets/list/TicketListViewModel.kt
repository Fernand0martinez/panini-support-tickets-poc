package com.panini.supporttickets.ui.tickets.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.panini.supporttickets.core.AppContainer
import com.panini.supporttickets.data.repository.TicketRepository
import com.panini.supporttickets.domain.model.Ticket
import com.panini.supporttickets.domain.model.TicketCategory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class TicketListUiState(
    val isLoading: Boolean = false,
    val tickets: List<Ticket> = emptyList(),
    val selectedCategory: TicketCategory? = null
)

class TicketListViewModel(
    private val repository: TicketRepository
) : ViewModel() {
    private val selectedCategory = MutableStateFlow<TicketCategory?>(null)

    val uiState: StateFlow<TicketListUiState> =
        combine(repository.tickets, selectedCategory) { tickets, category ->
            TicketListUiState(
                tickets = if (category == null) tickets else tickets.filter { it.category == category },
                selectedCategory = category
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = TicketListUiState(isLoading = true)
        )

    fun selectCategory(category: TicketCategory?) {
        selectedCategory.value = category
    }

    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T =
                TicketListViewModel(AppContainer.ticketRepository) as T
        }
    }
}
