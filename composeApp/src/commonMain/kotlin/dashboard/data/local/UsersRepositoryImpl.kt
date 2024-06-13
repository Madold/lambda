package dashboard.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.programmingmasters.lambda.cache.LambdaDatabase
import dashboard.domain.local.UsersRepository
import dashboard.domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UsersRepositoryImpl(private val db: LambdaDatabase): UsersRepository {
    
    private val queries = db.lambdaDatabaseQueries
    override fun getAllUsers(): Flow<List<User>> {
        return queries
            .getAllUsers()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map {
                it.map { user ->
                    User(
                        id = user.id,
                        name = user.name,
                        lastName = user.lastname,
                        email = user.email,
                        rating = user.rating.toFloat()
                    )
                }
            }
        
    }
    
    override suspend fun insertUser(user: User) {
        queries.insertUser(
            id = user.id,
            name = user.name,
            lastname = user.lastName,
            email = user.email,
            rating = user.rating.toDouble()
        )
    }

    override suspend fun deleteAllUsers() {
        queries.deleteAllUsers()
    }


}