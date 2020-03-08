package ua.cuscak.appliftingspacex.ui.rockets.overview

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ua.cuscak.appliftingspacex.database.getDatabase
import ua.cuscak.appliftingspacex.domain.Rocket
import ua.cuscak.appliftingspacex.network.NetworkRocket
import ua.cuscak.appliftingspacex.network.SpaceApi
import ua.cuscak.appliftingspacex.network.asDomainModel
import ua.cuscak.appliftingspacex.repository.SpaceRepository
import java.io.IOException

enum class SpaceApiStatus { LOADING, ERROR, DONE }

/**
 * The [ViewModel] that is attached to the [RocketOverviewFragment].
 */
class RocketOverviewViewModel(application: Application): AndroidViewModel(application){

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<SpaceApiStatus>()
    // The external immutable LiveData for the request status
    val status : LiveData<SpaceApiStatus>
        get() = _status

//    private val _rockets = MutableLiveData<List<Rocket>>()
//    val rocketsOverview: LiveData<List<Rocket>>
//        get() = _rockets

    private val _navigateToSelectedRocket = MutableLiveData<String>()
    val navigateToSelectedRocket: LiveData<String>
        get() = _navigateToSelectedRocket

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val spaceRepository = SpaceRepository(getDatabase(application))

    /**
     * A list of rockets displayed on the screen.
     */
    val rocketsOverview = spaceRepository.rockets

    /**
     * Call getRockets() on init so we can display status immediately.
     */
    init {
        //getRockets()
        getRocketsFromRepo()
    }

    /**
     * Refresh data from the repository. Use a coroutine launch to run in a
     * background thread.
     */
    private fun getRocketsFromRepo() {
        coroutineScope.launch {
            try {

                spaceRepository.refreshRockets()

            } catch (networkError: IOException){
                if(rocketsOverview.value.isNullOrEmpty()){
                    _status.value = SpaceApiStatus.ERROR
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayRocketDetails(name: String) {
        _navigateToSelectedRocket.value = name
    }

    fun displayRocketsDetailsComplete() {
        _navigateToSelectedRocket.value = null
    }


    /**
     * Factory for constructing this viewmodel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RocketOverviewViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RocketOverviewViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}