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
)
