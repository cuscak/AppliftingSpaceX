package ua.cuscak.appliftingspacex.network

import com.google.gson.annotations.SerializedName
import ua.cuscak.appliftingspacex.database.DatabaseLaunch
import ua.cuscak.appliftingspacex.domain.Launch

data class NetworkLaunch (
    @SerializedName("mission_name") val mission_name : String,
    @SerializedName("launch_year") val launch_year : Int,
    @SerializedName("links") val links : Links,
    @SerializedName("details") val details : String
)


data class Links (
    @SerializedName("mission_patch") val mission_patch : String,
    @SerializedName("mission_patch_small") val mission_patch_small : String,
    @SerializedName("reddit_campaign") val reddit_campaign : String,
    @SerializedName("reddit_launch") val reddit_launch : String,
    @SerializedName("reddit_recovery") val reddit_recovery : String,
    @SerializedName("reddit_media") val reddit_media : String,
    @SerializedName("presskit") val presskit : String,
    @SerializedName("article_link") val article_link : String,
    @SerializedName("wikipedia") val wikipedia : String,
    @SerializedName("video_link") val video_link : String,
    @SerializedName("youtube_id") val youtube_id : String,
    @SerializedName("flickr_images") val flickr_images : List<String>
)

/**
 * Convert Network results to database objects
 */
fun List<NetworkLaunch>.asDatabaseModel(): List<DatabaseLaunch>{
    return map {
        DatabaseLaunch(
            mission_name = it.mission_name,
            launch_year = it.launch_year,
            details = it.details,
            mission_patch = it.links.mission_patch
        )
    }
}

/**
 * Convert Network results to database objects
 */
fun List<NetworkLaunch>.asDomainModel(): List<Launch>{
    return map {
        Launch(
            missionName = it.mission_name,
            launchYear = it.launch_year,
            details = it.details,
            missionPatch = it.links.mission_patch
        )
    }
}