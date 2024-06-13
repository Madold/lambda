package dashboard.domain.model

data class Mentoring(
    val date: String,
    val duration: Long,
    val price: Long,
    val totalRevenue: Long,
    val userId: String
)