package dashboard.domain.model

import com.programmingmasters.lambda.cache.Mentoring_entity

data class Mentoring(
    val id: Int = 0,
    val date: String,
    val duration: Long,
    val price: Double,
    val totalRevenue: Double,
    val userId: String
)

fun Mentoring_entity.toDomainModel(): Mentoring {

    return Mentoring(
        id = this.id.toInt(),
        date = this.date,
        duration = this.duration,
        price = this.price,
        totalRevenue = this.totalReveune,
        userId = this.userId
    )

}