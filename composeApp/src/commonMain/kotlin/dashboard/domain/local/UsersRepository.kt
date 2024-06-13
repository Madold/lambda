package dashboard.domain.local

import dashboard.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UsersRepository {
    
    fun getAllUsers(): Flow<List<User>>
    suspend fun insertUser(user: User)
    suspend fun deleteAllUsers()
    
}