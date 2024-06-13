package dashboard.presentation

sealed interface DashboardEvent {
    data class ChangeUserQuery(val query: String): DashboardEvent
}