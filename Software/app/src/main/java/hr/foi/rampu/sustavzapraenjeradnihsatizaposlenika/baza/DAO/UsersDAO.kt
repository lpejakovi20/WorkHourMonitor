package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities.User

@Dao
interface UsersDAO {
    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Int): User

    @Query("SELECT * FROM users WHERE email = :email")
    fun getUserByEmail(email: String): User

    @Query("SELECT * FROM users")
    fun getAllUsers(): List<User>

    @Insert(onConflict = REPLACE)
    fun insertUser(vararg user: User): List<Long>

    @Query("UPDATE users SET onJob = :onJob WHERE id = :userId")
    fun updateUseronJob(onJob:Boolean, userId: Int)
}