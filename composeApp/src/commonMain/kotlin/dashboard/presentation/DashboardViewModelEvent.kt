package dashboard.presentation

sealed interface DashboardViewModelEvent {
    
    data class UserNotFoundError(val errorMessage: String): DashboardViewModelEvent
    
}
