package dashboard.domain.local

import core.utils.Result
import dashboard.domain.model.Mentoring
import kotlinx.coroutines.flow.Flow

interface MentoringRepository {
    
    suspend fun insertMentoring(mentoring: Mentoring) 
    fun getAllMentories(): Flow<List<Mentoring>>
    suspend fun getMentoriesByUserId(userId: String): Result<List<Mentoring>>
    
    suspend fun updateMentory(mentoring: Mentoring)
    
    suspend fun deleteMentory(mentoringId: Int)
    
    suspend fun getAllMentoriesByRevenueAsc(): List<Mentoring>
    suspend fun getAllMentoriesByRevenueDesc(): List<Mentoring>
    suspend fun getAllMentoriesByDuration(): List<Mentoring>
}
