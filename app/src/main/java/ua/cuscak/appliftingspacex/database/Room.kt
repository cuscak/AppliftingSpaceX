package ua.cuscak.appliftingspacex.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SpaceDao {

    @Query("select * from rocket_table")
    fun getRockets():LiveData<List<DatabaseRocket>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rockets: List<DatabaseRocket>)
}

@Database(entities = arrayOf(DatabaseRocket::class), version = 1, exportSchema = false)
abstract class SpaceDatabase: RoomDatabase(){
    abstract val spaceDao: SpaceDao
}

private lateinit var INSTANCE: SpaceDatabase

fun getDatabase(context: Context): SpaceDatabase {
    synchronized(SpaceDatabase::class.java) {
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SpaceDatabase::class.java,
                "space_database").build()
        }
    }
    return INSTANCE
}