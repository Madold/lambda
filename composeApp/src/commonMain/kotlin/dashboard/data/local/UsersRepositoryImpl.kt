package dashboard.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.programmingmasters.lambda.cache.LambdaDatabase
import core.utils.Result
import dashboard.data.DatabaseDriverFactory
import dashboard.domain.local.UsersRepository
import dashboard.domain.model.User
import dashboard.domain.model.toUserDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.NullPointerException

class UsersRepositoryImpl(driverFactory: DatabaseDriverFactory): UsersRepository {

    private val database = LambdaDatabase(driverFactory.createDriver())
    private val queries = database.lambdaDatabaseQueries
    
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

    override suspend fun getUserById(id: String): Result<User> {
        return try {
            val user = queries
                    .getUserById(id)
                    .executeAsOne()
                    .toUserDomainModel()

            Result.Success(user)
        } catch (e: NullPointerException) {
            Result.Error("No se encontró usuario con el ID asociado")
        } catch (e: Exception) {
            Result.Error(e.message ?: "Unknow Error")
        }
    }

    override suspend fun getAllUsersAsc(): List<User> {
        return queries
            .getAllUsersAsc()
            .executeAsList()
            .map { it.toUserDomainModel() }
    }

    override suspend fun getAllUsersDesc(): List<User> {
        return queries
            .getAllUsersDesc()
            .executeAsList()
            .map { it.toUserDomainModel() }
    }

    override suspend fun getAllUsersByRating(): List<User> {
        return queries
            .getAllUsersByRating()
            .executeAsList()
            .map { it.toUserDomainModel() }
    }

    override suspend fun updateUser(user: User) {
        queries.updateUser(
            name = user.name,
            lastname = user.lastName,
            email = user.email,
            rating = user.rating.toDouble(),
            id = user.id
        )
    }

    override suspend fun deleteUserById(id: String) {
       queries.deleteUser(id)
    }


}