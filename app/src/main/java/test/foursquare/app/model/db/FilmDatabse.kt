package test.foursquare.app.model.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import test.foursquare.app.model.structures.*

@Database(
    entities = [MovieStruct::class, MovieDetailStruct::class],
    version = 1
)
abstract class FilmDatabse : RoomDatabase() {

    abstract fun getMovieDao(): MovieDao
    abstract fun getMovieDetailDao(): MovieDetailDao

    companion object {
        @Volatile
        private var instance: FilmDatabse? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                FilmDatabse::class.java,
                "Film.db"
            ).build()
    }
}