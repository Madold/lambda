package dashboard.presentation

import dashboard.domain.model.Mentoring
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
    val usersSortType: UsersListSortType = UsersListSortType.NameAsc,
    val mentories: List<Mentoring> = listOf(),
    val mentoriesQuery: String = "",
    val mentoringDate: String = "",
    val mentoringDuration: Long = 0L,
    val mentoringPrice: Double = 0.0,
    val mentoringTotalReveune: Double = 0.0,
    val mentoringUserId: String = "",
    val mentoringListSortType: MentoringListSortType = MentoringListSortType.Duration,
    val filteredMentories: List<Mentoring> = listOf(),
    val isAddMentoringDialogVisible: Boolean = false
)
