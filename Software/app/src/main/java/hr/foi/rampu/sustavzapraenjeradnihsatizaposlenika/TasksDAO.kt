package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface TasksDAO {
    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getTask(id: Int): Task

    @Query("SELECT * FROM tasks WHERE user_id = :user")
    fun getAllTasksOfUser(user: Int): List<Task>

    @Query("SELECT * FROM tasks")
    fun getAllTasks(): List<Task>

    @Insert(onConflict = REPLACE)
    fun insertTask(vararg task: Task): List<Long>

    @Delete
    fun removeTask(vararg task: Task)

    @Query("DELETE FROM tasks WHERE user_id = :user")
    fun deleteTasksOfUser(user: Int)
}