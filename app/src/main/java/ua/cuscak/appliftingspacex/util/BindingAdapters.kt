package ua.cuscak.appliftingspacex.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import ua.cuscak.appliftingspacex.R
import ua.cuscak.appliftingspacex.domain.Launch
import ua.cuscak.appliftingspacex.domain.Rocket
import ua.cuscak.appliftingspacex.network.SpaceApiStatus
import ua.cuscak.appliftingspacex.ui.launches.LaunchItemAdapter
import ua.cuscak.appliftingspacex.ui.rockets.overview.RocketItemAdapter

/**
 * When there is no Rocket data (data is null), hide the [RecyclerView], otherwise show it.
 */
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Rocket>?){
    val adapter = recyclerView.adapter as RocketItemAdapter
    adapter.submitList(data)
}

@BindingAdapter("listLaunches")
fun bindLaunchRecyclerView(recyclerView: RecyclerView, data: List<Launch>?){
    val adapter = recyclerView.adapter as LaunchItemAdapter
    adapter.submitList(data)
}


/**
 * Uses the Glide library to load an image by URL into an [ImageView]
 */
@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    imgUrl?.let {
        Glide.with(imgView.context)
            .load(it)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

@BindingAdapter("patchImg")
fun bindPatchImg(imgView: ImageView, imgUrl: String?) {
    if (imgUrl.isNullOrEmpty()){
        imgView.setImageDrawable(imgView.context.getDrawable(R.drawable.ic_broken_image))
    } else{
        Glide.with(imgView.context)
            .load(imgUrl)
            .apply(
                RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image))
            .into(imgView)
    }
}

/**
 * This binding adapter displays the [SpaceApiStatus] of the network request in an image view.  When
 * the request is loading, it displays a loading_animation.  If the request has an error, it
 * displays a broken image to reflect the connection error.  When the request is finished, it
 * hides the image view.
 */
@BindingAdapter("spaceApiStatus")
fun bindStatus(statusImageView: ImageView, status: SpaceApiStatus?){
    when (status) {
        SpaceApiStatus.LOADING -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.loading_animation)
        }
        SpaceApiStatus.ERROR -> {
            statusImageView.visibility = View.VISIBLE
            statusImageView.setImageResource(R.drawable.ic_connection_error)
        }
        SpaceApiStatus.DONE -> {
            statusImageView.visibility = View.GONE
        }
    }
}