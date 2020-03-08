package ua.cuscak.appliftingspacex.network

import com.google.gson.annotations.SerializedName
import ua.cuscak.appliftingspacex.domain.Rocket


/**
 * Network objects that parse or prepare network calls.
 * Responsible for parsing responses from the server
 * or formatting objects to send to the server.
 *
 * @see database for objects that are mapped to the database
 * @see domain package for objects that are used in app
 */
data class NetworkRocket(
    @SerializedName("id") val id : Int,
    @SerializedName("active") val active : Boolean,
    @SerializedName("stages") val stages : Int,
    @SerializedName("boosters") val boosters : Int,
    @SerializedName("cost_per_launch") val cost_per_launch : Int,
    @SerializedName("success_rate_pct") val success_rate_pct : Int,
    @SerializedName("first_flight") val first_flight : String,
    @SerializedName("country") val country : String,
    @SerializedName("company") val company : String,
    @SerializedName("flickr_images") val flickr_images : List<String>,
    @SerializedName("wikipedia") val wikipedia : String,
    @SerializedName("description") val description : String,
    @SerializedName("rocket_id") val rocket_id : String,
    @SerializedName("rocket_name") val rocket_name : String,
    @SerializedName("rocket_type") val rocket_type : String
){

    val mainImage
        get() = flickr_images.random()
}

/**
 * Convert Network result to database object
 */
fun List<NetworkRocket>.asDomainModel(): List<Rocket> {
    return map {
        Rocket(
            id = it.rocket_id,
            name = it.rocket_name,
            description = it.description,
            imageUrl = it.mainImage,
            active = it.active,
            cost_per_launch = it.cost_per_launch
        )
    }
}

fun NetworkRocket.asDomainModel(): Rocket {
    return Rocket(
        id = rocket_id,
        name = rocket_name,
        description = description,
        imageUrl = mainImage,
        active = active,
        cost_per_launch = cost_per_launch
    )
}