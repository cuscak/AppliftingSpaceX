package ua.cuscak.appliftingspacex.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.cuscak.appliftingspacex.database.SpaceDatabase
import ua.cuscak.appliftingspacex.database.asDomainModel
import ua.cuscak.appliftingspacex.domain.Launch
import ua.cuscak.appliftingspacex.domain.Rocket
import ua.cuscak.appliftingspacex.network.NetworkLaunch
import ua.cuscak.appliftingspacex.network.SpaceApi
import ua.cuscak.appliftingspacex.network.asDatabaseModel

/**
 * Repository for fetching rockets/launches from the network and storing them on disk
 */
class SpaceRepository(private val database: SpaceDatabase) {

    val rockets: LiveData<List<Rocket>> = Transformations.map(database.spaceDao.getRockets()) {
        it.asDomainModel()
    }

    val launches: LiveData<List<Launch>> = Transformations.map(database.spaceDao.getLaunches()){
        it.asDomainModel()
    }

    /**
     * Refresh the rockets stored in the offline cache.
     *
     * This function uses the IO dispatcher to ensure the database insert database operation
     * happens on the IO dispatcher. By switching to the IO dispatcher using `withContext` this
     * function is now safe to call from any thread including the Main thread.
     *
     */
    suspend fun refreshRockets() {
        withContext(Dispatchers.IO) {
            val rockets = SpaceApi.retrofitService.getRocketsAsync().await()
            database.spaceDao.insertAll(rockets.asDatabaseModel())
        }
    }

    /**
     * Refresh the launches stored in the offline cache.
     */

    suspend fun refreshLaunches(){
        withContext(Dispatchers.IO){
            val launches = SpaceApi.retrofitService.getAllLaunches().await()
            database.spaceDao.insertAllLaunches(launches.asDatabaseModel())
        }
    }
}