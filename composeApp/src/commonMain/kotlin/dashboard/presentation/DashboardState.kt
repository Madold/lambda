package dashboard.presentation

import dashboard.domain.model.User

data class DashboardState(
    val users: List<User> = listOf(),
    val usersQuery: String = "",
    val userId: String = "",
    val userName: String = "",
    val userLastName: String = "",
    val userEmail: String = "",
    val userRating: Float = 0.0f,
    val idError: String? = null,
    val nameError: String? = null,
    val lastNameError: String? = null,
    val emailError: String? = null,
    val isAddUserDialogVisible: Boolean = false,
    val filteredUsers: List<User> = listOf(),
    val usersSortType: UsersListSortType = UsersListSortType.NameAsc
)
