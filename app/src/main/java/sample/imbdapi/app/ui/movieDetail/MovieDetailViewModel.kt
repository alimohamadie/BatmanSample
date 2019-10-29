package sample.imbdapi.app.ui.movieDetail

import androidx.lifecycle.ViewModel
import sample.imbdapi.app.model.MovieRepository

class MovieDetailViewModel(private val movieRepository: MovieRepository) : ViewModel() {

    suspend fun getMovieDetail(imbdId: String) =
        movieRepository.fetchMovieDetail(
            imbdId
        )
}