package dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.utils.Result
import dashboard.domain.local.MentoringRepository
import dashboard.domain.local.UsersRepository
import dashboard.domain.model.Mentoring
import dashboard.domain.model.User
import dashboard.domain.use_cases.ValidateEmail
import dashboard.domain.use_cases.ValidateId
import dashboard.domain.use_cases.ValidateLastName
import dashboard.domain.use_cases.ValidateName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val usersRepository: UsersRepository,
    private val mentoringRepository: MentoringRepository,
    private val validateId: ValidateId,
    private val validateName: ValidateName,
    private val validateLastName: ValidateLastName,
    private val validateEmail: ValidateEmail
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()
    private val channel = Channel<DashboardViewModelEvent>()
    val events = channel.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.getAllUsers().collectLatest { usersList ->
                _state.update {
                    it.copy(
                        users = usersList
                    )
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            mentoringRepository.getAllMentories().collectLatest { mentories ->
                _state.update {
                    it.copy(
                        mentories = mentories
                    )
                }
            }
        }

    }

    fun onEvent(event: DashboardEvent) {
        when (event) {
            is DashboardEvent.ChangeUserQuery -> {
                _state.update {
                    it.copy(
                        usersQuery = event.query
                    )
                }
            }

            is DashboardEvent.ChangeUserEmail -> {
                _state.update {
                    it.copy(
                        userEmail = event.email
                    )
                }
            }

            is DashboardEvent.ChangeUserId -> {
                _state.update {
                    it.copy(
                        userId = event.id
                    )
                }
            }

            is DashboardEvent.ChangeUserLastName -> {
                _state.update {
                    it.copy(
                        userLastName = event.lastName
                    )
                }
            }

            is DashboardEvent.ChangeUserName -> {
                _state.update {
                    it.copy(
                        userName = event.name
                    )
                }
            }

            is DashboardEvent.ChangeUserRating -> {
                _state.update {
                    it.copy(
                        userRating = event.rating
                    )
                }
            }

            is DashboardEvent.SaveUser -> {

                val idValidationResult = validateId(state.value.userId)
                val nameValidationResult = validateName(state.value.userName)
                val lastNameValidationResult = validateLastName(state.value.userLastName)
                val emailValidationResult = validateEmail(state.value.userEmail)

                val isAnyError = listOf(
                    idValidationResult,
                    nameValidationResult,
                    lastNameValidationResult,
                    emailValidationResult
                ).any { !it.success }

                if (isAnyError) {
                    _state.update {
                        it.copy(
                            idError = idValidationResult.errorMessage,
                            nameError = nameValidationResult.errorMessage,
                            lastNameError = lastNameValidationResult.errorMessage,
                            emailError = emailValidationResult.errorMessage
                        )
                    }
                    return
                }

                clearErrors()

                saveUser(
                    User(
                        id = state.value.userId,
                        name = state.value.userName,
                        lastName = state.value.userLastName,
                        email = state.value.userEmail,
                        rating = state.value.userRating
                    )
                )
            }

            is DashboardEvent.ChangeAddUserDialogVisibility -> {
                _state.update {
                    it.copy(
                        isAddUserDialogVisible = event.isVisible
                    )
                }
            }

            is DashboardEvent.SearchUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    when (val result = usersRepository.getUserById(state.value.usersQuery)) {
                        is Result.Error -> channel.send(
                            DashboardViewModelEvent.UserNotFoundError(
                                result.message ?: "Unknown Error"
                            )
                        )

                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    filteredUsers = listOf(result.data ?: return@launch)
                                )
                            }
                        }
                    }
                }
            }

            is DashboardEvent.ClearUserQuery -> {
                _state.update {
                    it.copy(
                        usersQuery = "",
                        filteredUsers = emptyList()
                    )
                }
            }

            is DashboardEvent.ChangeUserSortType -> {
                _state.update {
                    it.copy(
                        usersSortType = event.sortType
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    val filteredUsers = when (event.sortType) {
                        UsersListSortType.NameAsc -> usersRepository.getAllUsersAsc()
                        UsersListSortType.NameDesc -> usersRepository.getAllUsersDesc()
                        UsersListSortType.Rating -> usersRepository.getAllUsersByRating()
                    }
                    _state.update {
                        it.copy(
                            filteredUsers = filteredUsers
                        )
                    }
                }
            }

            DashboardEvent.ClearUserSortType -> {
                _state.update {
                    it.copy(
                        filteredUsers = emptyList(),
                        usersSortType = UsersListSortType.NameAsc
                    )
                }
            }

            is DashboardEvent.UpdateUser -> {

                viewModelScope.launch(Dispatchers.IO) {
                    usersRepository.updateUser(event.user)
                }

            }

            is DashboardEvent.DeleteUser -> {
                viewModelScope.launch(Dispatchers.IO) {
                    usersRepository.deleteUserById(event.id)
                }
            }

            is DashboardEvent.ChangeMentoriesQuery -> {
                _state.update {
                    it.copy(
                        mentoriesQuery = event.query
                    )
                }
            }
            is DashboardEvent.ChangeMentoringDate -> {
                _state.update {
                    it.copy(
                        mentoringDate = event.date
                    )
                }
            }
            is DashboardEvent.ChangeMentoringDuration -> {
                _state.update {
                    it.copy(
                        mentoringDuration = event.duration
                    )
                }
            }
            is DashboardEvent.ChangeMentoringPrice -> {
                _state.update {
                    it.copy(
                        mentoringPrice = event.price
                    )
                }
            }
            is DashboardEvent.ChangeMentoringSortType -> {
                _state.update {
                    it.copy(
                        mentoringListSortType = event.sortType
                    )
                }

                viewModelScope.launch(Dispatchers.IO) {
                    val filteredMentories =  when (event.sortType) {
                        MentoringListSortType.TotalReveuneAsc -> mentoringRepository.getAllMentoriesByRevenueAsc()
                        MentoringListSortType.TotalReveuneDesc -> mentoringRepository.getAllMentoriesByRevenueDesc()
                        MentoringListSortType.Duration -> mentoringRepository.getAllMentoriesByDuration()
                    }

                    _state.update {
                        it.copy(
                            filteredMentories = filteredMentories
                        )
                    }
                }

            }
            is DashboardEvent.ChangeMentoringTotalRevenue -> {
                _state.update {
                    it.copy(
                        mentoringTotalReveune = event.revenue
                    )
                }
            }
            is DashboardEvent.ChangeMentoringUserId -> {
                _state.update {
                    it.copy(
                        mentoringUserId = event.userId
                    )
                }
            }

            DashboardEvent.SaveMentoring -> {
                viewModelScope.launch(Dispatchers.IO) {
                    mentoringRepository.insertMentoring(
                        Mentoring(
                            date = state.value.mentoringDate,
                            duration = state.value.mentoringDuration,
                            price = state.value.mentoringPrice,
                            totalRevenue = state.value.mentoringTotalReveune,
                            userId = state.value.mentoringUserId
                        )
                    )
                }

                _state.update {
                    it.copy(
                        isAddMentoringDialogVisible = false
                    )
                }
            }

            is DashboardEvent.SearchMentoring -> {
                viewModelScope.launch(Dispatchers.IO) {
                    when (val result = mentoringRepository.getMentoriesByUserId(state.value.mentoriesQuery)) {
                        is Result.Error -> {

                        }
                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    filteredMentories = result.data ?: emptyList()
                                )
                            }
                        }
                    }
                }
            }

            is DashboardEvent.ChangeAddMentoringDialogVisibility -> {
                _state.update {
                    it.copy(
                        isAddMentoringDialogVisible = event.isVisible
                    )
                }
            }

            is DashboardEvent.ClearMentoringQuery -> {
                _state.update {
                    it.copy(
                        mentoriesQuery = "",
                        filteredMentories = emptyList()
                    )
                }
            }

            is DashboardEvent.UpdateMentoring -> {

                viewModelScope.launch(Dispatchers.IO) {
                    mentoringRepository.updateMentory(event.mentoring)
                }
            }

            is DashboardEvent.DeleteMentoringbyId -> {
                viewModelScope.launch(Dispatchers.IO) {
                    mentoringRepository.deleteMentory(event.mentoringId)
                }
            }
        }
    }


    private fun saveUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            usersRepository.insertUser(user)
            clearFields()
        }
    }

    private fun clearFields() {
        _state.update {
            it.copy(
                userId = "",
                userName = "",
                userLastName = "",
                userEmail = "",
                userRating = 0.0f,
                isAddUserDialogVisible = false
            )
        }
    }

    private fun clearErrors() {
        _state.update {
            it.copy(
                idError = null,
                nameError = null,
                lastNameError = null,
                emailError = null
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        channel.close()
    }

}