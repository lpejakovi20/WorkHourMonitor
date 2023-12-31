package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.DAO

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.Task

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

    @Query("UPDATE tasks SET text =:sText WHERE ID = :sID")
    fun update(sID: Int, sText: String?)
}