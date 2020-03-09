package ua.cuscak.appliftingspacex.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Dao
interface SpaceDao {

    @Query("select * from rocket_table")
    fun getRockets():LiveData<List<DatabaseRocket>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(rockets: List<DatabaseRocket>)

    @Query("select * from launches_table")
    fun getLaunches():LiveData<List<DatabaseLaunch>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllLaunches(launches: List<DatabaseLaunch>)
}

@Database(entities = arrayOf(DatabaseRocket::class, DatabaseLaunch::class), version = 2, exportSchema = false)
abstract class SpaceDatabase: RoomDatabase(){
    abstract val spaceDao: SpaceDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("CREATE TABLE `launches_table` (`mission_name` TEXT NOT NULL, `launch_year` INTEGER NOT NULL, `details` TEXT NOT NULL, `mission_patch` TEXT NOT NULL," +
                "PRIMARY KEY(`mission_name`))")
    }
}

private lateinit var INSTANCE: SpaceDatabase

fun getDatabase(context: Context): SpaceDatabase {
    synchronized(SpaceDatabase::class.java) {
        if (!::INSTANCE.isInitialized){
            INSTANCE = Room.databaseBuilder(context.applicationContext, SpaceDatabase::class.java, "space_database")
                //.addMigrations(MIGRATION_1_2)
                .build()
        }
    }
    return INSTANCE
}