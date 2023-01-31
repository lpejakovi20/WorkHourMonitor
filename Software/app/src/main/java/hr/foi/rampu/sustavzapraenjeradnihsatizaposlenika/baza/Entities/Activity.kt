package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika.baza.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "activity",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.RESTRICT
    )]
)
data class Activity (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "text") private var text: String,
    @ColumnInfo(name="start_of_activity") val start_of_activity: String,
    @ColumnInfo(name="end_of_activity") val end_of_activity: String,
    @ColumnInfo(name = "user_id") val userId: Int,

)
{
    override fun toString(): String {
        return text;
    }

    //generate getter abd setter
    fun getID(): Int {
        return id
    }

    fun getText(): String? {
        return text
    }

    fun setText(text: String?) {
        this.text = text!!
    }
}