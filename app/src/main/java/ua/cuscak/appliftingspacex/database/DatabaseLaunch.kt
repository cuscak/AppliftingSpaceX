package ua.cuscak.appliftingspacex.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import ua.cuscak.appliftingspacex.domain.Launch

@Entity(tableName = "launches_table")
data class DatabaseLaunch(
    @PrimaryKey
    val mission_name : String,
    val launch_year : Int,
    val details : String?,
    val mission_patch : String?
)

/**
 * Map DatabaseRockets to domain entities
 */
fun List<DatabaseLaunch>.asDomainModel(): List<Launch>{
    //the conversion is simple, and some of this isn't necessary for this simple example
    //in a production app, the structure of the domain, database, and network objects could be very different
    //and might required conversion logic
    return map {
        Launch(
            missionName = it.mission_name,
            launchYear = it.launch_year,
            details = it.details ?: "NA",
            missionPatch = it.mission_patch ?: "NA"
        )
    }
}