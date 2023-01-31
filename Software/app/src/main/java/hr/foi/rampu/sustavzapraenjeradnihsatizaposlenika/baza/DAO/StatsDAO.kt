package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.Stats

@Dao
interface StatsDAO {
    @Query("SELECT * FROM stats")
    fun getAllStats(): List<Stats>

    @Insert(onConflict = REPLACE)
    fun insertStats(vararg stats: Stats): List<Long>

    @Insert
    fun insertStat(vararg stats: Stats)

    @Delete
    fun reset(mainData: List<Stats>)

    data class TotalHoursAndOvertime(
        val totalHours: Int,
        val totalOvertime: Int,
        val totalSundayOrNightShift: Int
    )

    @Query("SELECT SUM(hours_done) as totalHours, SUM(overtime_hours) as totalOvertime, SUM(hours_sunday_or_night_shift) as totalSundayOrNightShift FROM Stats WHERE user_id = :userId AND strftime('%m', start_time) = :month AND strftime('%Y', start_time) = :year")
    fun getTotalHoursAndOvertimeByMonthAndYear(userId: Int, month: String, year: String): TotalHoursAndOvertime

    @Query("SELECT SUM(hours_done) as totalHours, SUM(overtime_hours) as totalOvertime, SUM(hours_sunday_or_night_shift) as totalSundayOrNightShift FROM Stats WHERE user_id = :userId AND strftime('%m', start_time) = :month AND strftime('%Y', start_time) = :year")
    fun getStatsByUser(userId: Int, month: String, year: String): TotalHoursAndOvertime


    @Query("UPDATE stats SET hours_done = :newHoursDone,overtime_hours =:overtime,hours_sunday_or_night_shift =:sunday WHERE start_time = :startTime AND user_id = :userId")
    fun updateHoursDoneWithCondition(startTime: String, newHoursDone: Int,overtime:Int,sunday : Int, userId: Int)

    @Query("INSERT INTO stats VALUES (:id, :start_time, :end_time,:hours_done,:overtime_hours,:hours_sunday_or_night_shift,:userId)")
    fun insertteststat(id: Int, start_time : String, end_time : String,hours_done : Int,overtime_hours : Int,hours_sunday_or_night_shift : Int,userId: Int)
}


