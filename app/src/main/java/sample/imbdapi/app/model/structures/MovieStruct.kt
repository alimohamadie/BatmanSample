package sample.imbdapi.app.model.structures

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = "movies"
)
data class MovieStruct(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("imdbID")
    var id: String,
    var Title: String,
    var Year: String,
    var Type: String,
    var Poster: String
) {
    var created_at: Long = System.currentTimeMillis()
        get() = if (field > 0) field else {
            field = System.currentTimeMillis()
            field
        }
}