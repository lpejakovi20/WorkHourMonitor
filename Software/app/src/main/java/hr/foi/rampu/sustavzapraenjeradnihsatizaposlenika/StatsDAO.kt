package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface StatsDAO {
    @Query("SELECT * FROM stats")
    fun getAllStats(): List<Stats>

    @Insert(onConflict = REPLACE)
    fun insertStats(vararg stats: Stats): List<Long>

    @Delete
    fun reset(mainData: List<Stats>)

    data class TotalHoursAndOvertime(
        val totalHours: Int,
        val totalOvertime: Int,
        val totalSundayOrNightShift: Int
    )

    @Query("SELECT SUM(hours_done) as totalHours, SUM(overtime_hours) as totalOvertime, SUM(hours_sunday_or_night_shift) as totalSundayOrNightShift FROM Stats WHERE user_id = :userId AND strftime('%m', start_time) = :month AND strftime('%Y', start_time) = :year")
    fun getTotalHoursAndOvertimeByMonthAndYear(userId: Int, month: String, year: String): TotalHoursAndOvertime


}