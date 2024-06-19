package dashboard.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.programmingmasters.lambda.cache.LambdaDatabase
import com.programmingmasters.lambda.cache.Mentoring_entity
import core.utils.Result
import dashboard.data.DatabaseDriverFactory
import dashboard.domain.local.MentoringRepository
import dashboard.domain.model.Mentoring
import dashboard.domain.model.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.lang.Exception

class MentoringRepositoryImpl(driverFactory: DatabaseDriverFactory): MentoringRepository {
    
    private val db = LambdaDatabase(driverFactory.createDriver())
    private val queries = db.lambdaDatabaseQueries
    
    override suspend fun insertMentoring(mentoring: Mentoring) {
        queries.insertMentory(
            date = mentoring.date,
            duration = mentoring.duration,
            price = mentoring.price,
            totalReveune = mentoring.totalRevenue,
            userId = mentoring.userId
        )
    }

    override fun getAllMentories(): Flow<List<Mentoring>> {
        return queries
            .getAllMentories()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { it.map { mentoring -> mentoring.toDomainModel() } }}

    override suspend fun getMentoriesByUserId(userId: String): Result<List<Mentoring>> {
        return try {
        
            val mentories = queries
                .getMentoriesByUserId(userId)
                .executeAsList()
                .map {
                    it.toDomainModel()
                }
                
            
            Result.Success(mentories)
        } catch (e: NullPointerException) {
            Result.Error("No se encontró tutorias con el id del usuario proporcionado")
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }     
    }

    override suspend fun updateMentory(mentoring: Mentoring) {
        queries.updateMentory(
            date = mentoring.date,
            duration = mentoring.duration,
            price = mentoring.price,
            totalReveune = mentoring.totalRevenue,
            id = mentoring.id.toLong()
        )
    }

    override suspend fun deleteMentory(mentoringId: Int) {
        queries.deleteMentory(mentoringId.toLong())
    }

    override suspend fun getAllMentoriesByRevenueAsc(): List<Mentoring> {
        return queries
            .getAllMentoriesByRevenueAsc()
            .executeAsList()
            .map {
                it.toDomainModel()
            }
    }

    override suspend fun getAllMentoriesByRevenueDesc(): List<Mentoring> {
        return queries
            .getAllMentoriesByRevenueDesc()
            .executeAsList()
            .map {
                it.toDomainModel()
            }
    }

    override suspend fun getAllMentoriesByDuration(): List<Mentoring> {
        return queries
            .getAllMentoriesByDuration()
            .executeAsList()
            .map {
                it.toDomainModel()
            }
    }
}

   