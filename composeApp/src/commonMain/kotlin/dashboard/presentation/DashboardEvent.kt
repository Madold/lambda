package dashboard.presentation

import dashboard.domain.model.Donation
import dashboard.domain.model.Mentoring
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
    data class ChangeMentoriesQuery(val query: String): DashboardEvent
    data class ChangeMentoringDate(val date: String): DashboardEvent
    data class ChangeMentoringDuration(val duration: Long): DashboardEvent
    data class ChangeMentoringPrice(val price: Double): DashboardEvent
    data class ChangeMentoringTotalRevenue(val revenue: Double): DashboardEvent
    data class ChangeMentoringUserId(val userId: String): DashboardEvent
    data class ChangeMentoringSortType(val sortType: MentoringListSortType): DashboardEvent
    data object SaveMentoring: DashboardEvent
    data object SearchMentoring: DashboardEvent
    data class ChangeAddMentoringDialogVisibility(val isVisible: Boolean): DashboardEvent
    data object ClearMentoringQuery: DashboardEvent
    data class UpdateMentoring(val mentoring: Mentoring): DashboardEvent
    data class DeleteMentoringbyId(val mentoringId: Int): DashboardEvent
    data class ChangeDonationUserId(val userId: String): DashboardEvent
    data class ChangeDonationMentoringId(val mentoringId: Int): DashboardEvent
    data class ChangeDonationAmount(val amount: Double): DashboardEvent
    data object SaveDonation: DashboardEvent
    data class ChangeDonationsQuery(val query: String): DashboardEvent
    data object SearchDonations: DashboardEvent
    data object ClearDonationsQuery: DashboardEvent
    data class ChangeAddDonationDialogVisibility(val isVisible: Boolean): DashboardEvent
    data class ChangeDonationsSortType(val sortType: DonationListSortType): DashboardEvent
    data class UpdateDonation(val donation: Donation): DashboardEvent
    data class DeleteDonationByUserId(val userId: String): DashboardEvent
}