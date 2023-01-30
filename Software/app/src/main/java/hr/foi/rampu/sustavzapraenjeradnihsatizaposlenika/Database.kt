package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

@androidx.room.Database(version = 1, entities = [Task::class, User::class, Activity::class,Stats::class], exportSchema = false)
abstract class Database : RoomDatabase() {
    abstract fun getTasksDAO(): TasksDAO
    abstract fun getUsersDAO(): UsersDAO
    abstract fun getActivitiesDAO(): ActivitiesDAO
    abstract fun getStatsDAO(): StatsDAO

    companion object {
        @Volatile
        private var implementedInstance: Database? = null
        fun getInstance(): Database {
            if (implementedInstance == null) {
                throw NullPointerException("Database instance has not yet been created!")
            }
            return implementedInstance!!
        }
        fun buildInstance(context: Context) {
            if (implementedInstance == null) {
                val instanceBuilder = Room.databaseBuilder(
                    context,
                    Database::class.java,
                    "baza022.db"
                )
                instanceBuilder.fallbackToDestructiveMigration()
                instanceBuilder.allowMainThreadQueries()
                instanceBuilder.build()
                implementedInstance = instanceBuilder.build()
            }
        }
    }

}