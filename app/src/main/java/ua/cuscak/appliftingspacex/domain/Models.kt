package ua.cuscak.appliftingspacex.domain

/**
 * Plain Kotlin data classes that represent the things in our app.
 * Used to be displayed on screen, or manipulated by the app.
 *
 * @see database for objects that are mapped to the database
 * @see network for objects that parse or prepare network calls
 */
data class Rocket(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val active : Boolean,
    val cost_per_launch : Int)

data class Launch(
    val missionName : String,
    val launchYear : Int,
    val details : String?,
    val missionPatch : String?
)