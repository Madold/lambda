package dashboard.domain.model

import com.programmingmasters.lambda.cache.Donation_entity

data class Donation(
    val userId: String,
    val mentoringId: Int,
    val amount: Double
)

fun Donation_entity.toDomainModel() = Donation(
    userId = userId,
    mentoringId = this.mentoringId.toInt(),
    amount = price
)