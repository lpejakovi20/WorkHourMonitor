package hr.foi.rampu.sustavzapraenjeradnihsatizaposlenika

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity(tableName = "tasks",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["id"],
        childColumns = ["user_id"],
        onDelete = ForeignKey.RESTRICT
    )]
)
data class Task (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "text") private var text: String,
    @ColumnInfo(name = "user_id") val userId: Int,
    val completed: Boolean
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
