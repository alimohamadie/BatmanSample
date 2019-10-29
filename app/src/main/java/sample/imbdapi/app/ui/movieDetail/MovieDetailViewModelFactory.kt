package sample.imbdapi.app.ui.movieDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import sample.imbdapi.app.model.MovieRepository

class MovieDetailViewModelFactory(
    private val movieRepository: MovieRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieDetailViewModel(movieRepository) as T
    }
}