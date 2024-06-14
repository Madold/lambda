package dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dashboard.domain.local.UsersRepository
import dashboard.domain.model.User
import dashboard.domain.use_cases.ValidateEmail
import dashboard.domain.use_cases.ValidateId
import dashboard.domain.use_cases.ValidateLastName
import dashboard.domain.use_cases.ValidateName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val usersRepository: UsersRepository,
    private val validateId: ValidateId,
    private val validateName: ValidateName,
    private val validateLastName: ValidateLastName,
    private val validateEmail: ValidateEmail
): ViewModel() {
    
    private val _state = MutableStateFlow(DashboardState())
    val state = _state.asStateFlow()

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

                viewModelScope.launch(Dispatchers.IO) {
                    usersRepository.insertUser(
                        User(
                            id = state.value.userId,
                            name = state.value.userName,
                            lastName = state.value.userLastName,
                            email = state.value.userEmail,
                            rating = state.value.userRating
                        )
                    )
                    clearFields()
                }
            }

            is DashboardEvent.ChangeAddUserDialogVisibility -> {
                _state.update {
                    it.copy(
                        isAddUserDialogVisible = event.isVisible
                    )
                }
            }
        }
    }
    
    private fun clearFields() {
        _state.update {
            it.copy(
                userId = "",
                userName = "",
                userLastName = "",
                userEmail = "",
                userRating = 0.0f
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

}