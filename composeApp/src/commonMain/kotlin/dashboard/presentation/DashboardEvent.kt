package dashboard.presentation

import dashboard.domain.model.User

sealed interface DashboardEvent {
    data class ChangeUserQuery(val query: String): DashboardEvent
    data class ChangeUserId(val id: String): DashboardEvent
    data class ChangeUserName(val name: String): DashboardEvent
    data class ChangeUserLastName(val lastName: String): DashboardEvent
    data class ChangeUserEmail(val email: String): DashboardEvent
    data class ChangeUserRating(val rating: Float): DashboardEvent
    data object SaveUser: DashboardEvent
    data class ChangeAddUserDialogVisibility(val isVisible: Boolean): DashboardEvent
    data object SearchUser: DashboardEvent
    data object ClearUserQuery: DashboardEvent

    data class ChangeUserSortType(val sortType: UsersListSortType): DashboardEvent

    data object ClearUserSortType: DashboardEvent
    data class UpdateUser(val user: User ): DashboardEvent
    data class DeleteUser(val id: String): DashboardEvent
}