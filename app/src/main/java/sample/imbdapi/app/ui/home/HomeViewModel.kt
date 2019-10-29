package sample.imbdapi.app.ui.home

import androidx.lifecycle.ViewModel
import sample.imbdapi.app.model.MovieRepository

class HomeViewModel(
    private val movieRepository: MovieRepository
) : ViewModel() {

    suspend fun fetchMovie(serialName: String) =
        movieRepository.fetchMovie(
            serialName
        )

    fun getMoviedList() = movieRepository.getMoviedList()


}