package test.foursquare.app.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import test.foursquare.app.model.MovieRepository

class HomeViewModelFactory(
    private val movieRepository: MovieRepository
) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(movieRepository) as T
    }
}