package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.JobStatus

@Dao
interface JobStatusDAO {
    @Query("SELECT * FROM jobStatus WHERE user_id = :id")
    fun getJobStatus(id: Int): JobStatus

    @Query("SELECT * FROM jobStatus")
    fun getAllJobStatuses(): List<JobStatus>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJobStatus(vararg user: JobStatus): List<Long>

    @Query("UPDATE jobStatus SET startTime = :time WHERE user_id = :userId")
    fun updateJobStatusOfUser(time:String, userId: Int)
}