package ua.cuscak.appliftingspacex.ui.launches

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ua.cuscak.appliftingspacex.database.getDatabase
import ua.cuscak.appliftingspacex.network.SpaceApiStatus
import ua.cuscak.appliftingspacex.repository.SpaceRepository
import java.io.IOException

class LaunchesViewModel(application: Application): AndroidViewModel(application)  {

    /**
     * The data source this ViewModel will fetch results from.
     */
    private val spaceRepository = SpaceRepository(getDatabase(application))


    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )


    /**
     * A list of launches displayed on the screen.
     */
    val launches = spaceRepository.launches

    init {
        getLaunchesFromRepo()
    }

    private fun getLaunchesFromRepo() {
        coroutineScope.launch {
            try {

                spaceRepository.refreshLaunches()

            } catch (networkError: IOException){

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    /**
     * Factory for constructing DevByteViewModel with parameter
     */
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LaunchesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LaunchesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}