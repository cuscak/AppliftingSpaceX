package ua.cuscak.appliftingspacex.models

import com.google.gson.annotations.SerializedName

data class Rocket(
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