package ua.cuscak.appliftingspacex.ui.rockets.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ua.cuscak.appliftingspacex.domain.Rocket
import ua.cuscak.appliftingspacex.network.NetworkRocket
import ua.cuscak.appliftingspacex.network.SpaceApi
import ua.cuscak.appliftingspacex.network.asDomainModel

/**
 * The [ViewModel] that is attached to the [RocketDetailFragment].
 */
class RocketDetailViewModel(val rocketId: String): ViewModel(){

    private val _rocket = MutableLiveData<Rocket>()
    val rocket: LiveData<Rocket>
        get() = _rocket


    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )

    /**
     * Call getRockets() on init so we can display status immediately.
     */
    init {
        getRocketDetail()
    }

    private fun getRocketDetail() {
        coroutineScope.launch{
            ////creates and starts the network call on a background thread
            var getRecipeDetailDeferred  = SpaceApi.retrofitService.getRocketDetailAsync(rocketId)

            try{
                var result = getRecipeDetailDeferred.await()
                _rocket.value = result.asDomainModel()
                Log.d("AAA", result.rocket_name)
            } catch (e: Exception) {
                Log.d(this@RocketDetailViewModel::class.java.name, e.message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}