package dashboard.domain.local

import dashboard.domain.model.Donation
import kotlinx.coroutines.flow.Flow

interface DonationsRespository {
    fun getAllDonations(): Flow<List<Donation>>
    suspend fun insertDonation(donation: Donation)
    suspend fun deleteAllDonations()
    suspend fun getDonationsByUserId(userId: String): List<Donation>
    suspend fun deleteDonation(userId: String)
    suspend fun getDonationsOrderedByMountAsc(): List<Donation>
    suspend fun getDonationsOrderedByMountDesc(): List<Donation>
    suspend fun deleteDonationByUserId(userId: String)
    
    suspend fun updateDonation(donation: Donation)
}
