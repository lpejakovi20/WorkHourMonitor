package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "stats",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.RESTRICT
    )]
)
data class Stats (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val start_time : String,
    val end_time : String,
    val hours_done : Int,
    val overtime_hours : Int,
    val hours_sunday_or_night_shift : Int,
    @ColumnInfo(name = "user_id") val userId: Int
    )