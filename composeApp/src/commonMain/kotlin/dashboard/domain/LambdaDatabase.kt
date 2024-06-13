package dashboard.domain

import dashboard.domain.model.User
import kotlinx.coroutines.flow.Flow

interface LambdaDatabase {
    suspend fun insertUser(user: User)
    suspend fun deleteAllUsers()
    fun getAllUsers(): Flow<List<User>>
}
