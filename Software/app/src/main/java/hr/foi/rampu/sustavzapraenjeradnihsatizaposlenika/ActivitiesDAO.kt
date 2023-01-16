package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import java.time.Year
import java.util.Date

@Dao
interface ActivitiesDAO {

    @Query("SELECT * FROM activity WHERE id = :id")
    fun getActivity(id: Int): Activity

    @Query("SELECT * FROM activity WHERE user_id = :user")
    fun getAllActivitiesOfUser(user: Int): List<Activity>

    @Query("SELECT * FROM activity")
    fun getAllActivities(): List<Activity>

    @Query("SELECT * FROM activity WHERE strftime('%w', start_of_activity) = :dayOfWeek AND strftime('%Y-%m-%d', start_of_activity) = :date")
    fun getActivitiesByDayOfWeekAndDate(dayOfWeek: Int, date: String): List<Activity>

    @Query("SELECT * FROM activity WHERE strftime('%Y', start_of_activity) = :year AND strftime('%m', start_of_activity) = :month AND strftime('%d', start_of_activity)= :day AND user_id = :user")
    fun getActivitiesByDate(year: String, month: String, day: String,user: Int): List<Activity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertActivity(vararg activity: Activity): List<Long>
}