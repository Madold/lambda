package dashboard.data.local

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.programmingmasters.lambda.cache.LambdaDatabase
import dashboard.data.DatabaseDriverFactory
import dashboard.domain.local.DonationsRespository
import dashboard.domain.model.Donation
import dashboard.domain.model.toDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DonationsRepositoryImpl(driverFactory: DatabaseDriverFactory) : DonationsRespository {
    
    private val db = LambdaDatabase(driverFactory.createDriver())
    private val queries = db.lambdaDatabaseQueries
    
    override fun getAllDonations(): Flow<List<Donation>> {
        return queries
            .getAllDonations()
            .asFlow()
            .mapToList(Dispatchers.IO)
            .map { entities -> 
                entities.map { it.toDomainModel() }
            }
    }

    override suspend fun insertDonation(donation: Donation) {
       queries.insertDonation(
           userId = donation.userId,
           price = donation.amount,
           mentoringId = donation.mentoringId.toLong(),
       )
    }

    override suspend fun deleteAllDonations() {
        queries.deleteAllDonations()
    }

    override suspend fun getDonationsByUserId(userId: String): List<Donation> {
        return queries
            .getDonationsByUserId(userId)
            .executeAsList()
            .map { it.toDomainModel() }
    }

    override suspend fun deleteDonation(userId: String) {
        queries.deleteDonation(userId)
    }

    override suspend fun getDonationsOrderedByMountAsc(): List<Donation> {
        return queries
            .getAllDonationsByMountAsc()
            .executeAsList()
            .map { it.toDomainModel() }
    }

    override suspend fun getDonationsOrderedByMountDesc(): List<Donation> {
        return queries
            .getAllDonationsByMountDesc()
            .executeAsList()
            .map { it.toDomainModel() }
    }

    override suspend fun deleteDonationByUserId(userId: String) {
        queries.deleteDonation(userId)
    }

    override suspend fun updateDonation(donation: Donation) {
        queries.updateDonation(
            price = donation.amount,
            userId = donation.userId
        )
    }

}

