package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "jobStatus",
    foreignKeys = [ForeignKey(
        entity = JobStatus::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.RESTRICT
    )]
)
data class JobStatus (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startTime : String,
    @ColumnInfo(name = "user_id") val userId: Int
)