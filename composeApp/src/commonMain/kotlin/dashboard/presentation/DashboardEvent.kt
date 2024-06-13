package dashboard.presentation

sealed interface DashboardEvent {
    data class ChangeUserQuery(val query: String): DashboardEvent
    data class ChangeUserId(val id: String): DashboardEvent
    data class ChangeUserName(val name: String): DashboardEvent
    data class ChangeUserLastName(val lastName: String): DashboardEvent
    data class ChangeUserEmail(val email: String): DashboardEvent
    data class ChangeUserRating(val rating: Float): DashboardEvent
    data object SaveUser: DashboardEvent
}