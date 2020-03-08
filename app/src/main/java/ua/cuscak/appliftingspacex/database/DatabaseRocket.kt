package ua.cuscak.appliftingspacex.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.cuscak.appliftingspacex.domain.Rocket

/**
 * DatabaseRocket represents a rocket entity in the database.
 */
@Entity(tableName = "rocket_table")
data class DatabaseRocket(
    @PrimaryKey
    val rocket_id : String,
    val active : Boolean,
    val cost_per_launch : Int,
    val image : String,
    val description : String,
    val rocket_name : String
)

/**
 * Map DatabaseRockets to domain entities
 */
fun List<DatabaseRocket>.asDomainModel(): List<Rocket>{
    //the conversion is simple, and some of this isn't necessary for this simple example
    //in a production app, the structure of the domain, database, and network objects could be very different
    //and might required conversion logic
    return map {
        Rocket(
            id = it.rocket_id,
            name = it.rocket_name,
            description = it.description,
            imageUrl = it.image,
            active = it.active,
            cost_per_launch = it.cost_per_launch
        )
    }
}