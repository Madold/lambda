package dashboard.domain.model

import com.programmingmasters.lambda.cache.User as UserDbModel
data class User(
    val id: String,
    val name: String,
    val lastName: String,
    val email: String,
    val rating: Float
)

fun User.toDatabaseModel(): UserDbModel {
    return UserDbModel(
        id = this.id,
        name = this.name,
        lastname = this.lastName,
        email = this.email,
        rating = this.rating.toDouble()
    )
}

fun UserDbModel.toUserDomainModel(): User {
    return User(
        id = this.id,
        name = this.name,
        lastName = this.lastname,
        email = this.email,
        rating = this.rating.toFloat()
    )
}