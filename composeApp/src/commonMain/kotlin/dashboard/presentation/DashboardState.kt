package dashboard.presentation

import dashboard.domain.model.User

data class DashboardState(
    val users: List<User> = listOf(),
    val usersQuery: String = "",
)
