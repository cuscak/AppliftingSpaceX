package ua.cuscak.appliftingspacex.ui.rockets.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ua.cuscak.appliftingspacex.models.Rocket
import ua.cuscak.appliftingspacex.network.SpaceApi

enum class SpaceApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [RocketOverviewFragment].
 */
class RocketOverviewViewModel: ViewModel(){

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<SpaceApiStatus>()
    // The external immutable LiveData for the request status
    val status : LiveData<SpaceApiStatus>
        get() = _status

    private val _rockets = MutableLiveData<List<Rocket>>()
    val rocketsOverview: LiveData<List<Rocket>>
        get() = _rockets

    private val _navigateToSelectedRocket = MutableLiveData<String>()
    val navigateToSelectedRocket: LiveData<String>
        get() = _navigateToSelectedRocket

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )

    /**
     * Call getRockets() on init so we can display status immediately.
     */
    init {
        getRockets()
    }

    /**
     * Sets the value of the status LiveData to the Space API status.
     */
    private fun getRockets() {
        coroutineScope.launch {
            //creates and starts the network call on a background thread
            var getPropertiesDeferred = SpaceApi.retrofitService.getRocketsAsync()

            try {
                _status.value = SpaceApiStatus.LOADING

                var listResult = getPropertiesDeferred.await()

                _status.value = SpaceApiStatus.DONE
                _rockets.value = listResult
            } catch (e: Exception) {
                _status.value = SpaceApiStatus.ERROR
                _rockets.value = emptyList()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayRecipeDetails(name: String) {
        _navigateToSelectedRocket.value = name
    }

    fun displayRecipeDetailsComplete() {
        _navigateToSelectedRocket.value = null
    }
}