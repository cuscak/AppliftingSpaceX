package ua.cuscak.appliftingspacex.ui.launches

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import ua.cuscak.appliftingspacex.database.getDatabase
import ua.cuscak.appliftingspacex.domain.Launch
import ua.cuscak.appliftingspacex.network.*
import ua.cuscak.appliftingspacex.repository.SpaceRepository
import java.io.IOException

class LaunchesViewModel(application: Application): AndroidViewModel(application)  {

    private val _launches = MutableLiveData<List<Launch>>()
    val launches: LiveData<List<Launch>>
        get() = _launches

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<SpaceApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<SpaceApiStatus>
        get() = _status


    /**
     * This is the job for all coroutines started by this ViewModel.
     *
     * Cancelling this job will cancel all coroutines started by this ViewModel.
     */
    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope( viewModelJob + Dispatchers.Main )


    init {
        //getLaunchesFromRepo(LaunchesApiFilter.SHOW_PAST)
        getLaunches(LaunchesApiFilter.SHOW_PAST)
    }

    private fun getLaunches(filter: LaunchesApiFilter) {
        coroutineScope.launch{
            var getLaunches = SpaceApi.retrofitService.getAllLaunches(filter.value)
            try {
                _status.value = SpaceApiStatus.LOADING
                var listResult = getLaunches.await()
                _status.value = SpaceApiStatus.DONE
                _launches.value = listResult.asDomainModel()
            } catch (networkException: IOException){
                _status.value = SpaceApiStatus.ERROR
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

    /**
     * Updates the data set filter for the web services by querying the data with the new filter
     * @param filter the [LaunchesApiFilter] that is sent as part of the web server request
     */
    fun updateFilter(filter: LaunchesApiFilter) {
        getLaunches(filter)
    }

}