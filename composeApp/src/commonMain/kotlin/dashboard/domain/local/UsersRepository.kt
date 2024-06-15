package dashboard.domain.local

import core.utils.Result
import dashboard.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    
    fun getAllUsers(): Flow<List<User>>
    suspend fun insertUser(user: User)
    suspend fun deleteAllUsers()
    
    suspend fun getUserById(id: String): Result<User>

    suspend fun getAllUsersAsc(): List<User>

    suspend fun getAllUsersDesc(): List<User>

    suspend fun getAllUsersByRating(): List<User>

    suspend fun updateUser(user: User)

    suspend fun deleteUserById(id: String)

}