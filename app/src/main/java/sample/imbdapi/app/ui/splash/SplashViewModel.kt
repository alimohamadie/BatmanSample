package sample.imbdapi.app.ui.splash

import androidx.lifecycle.ViewModel
import sample.imbdapi.app.model.GeneralRepository

class SplashViewModel(private val generalRepository: GeneralRepository) : ViewModel() {

    fun setFirstArrivalIfFlase() {
        if (!isFirstArrival())
            setFirstArrival(true)
    }

    fun isFirstArrival(): Boolean {
        return generalRepository.isFirstArrival()
    }



    fun setFirstArrival(isFirstArrival: Boolean) {
        generalRepository.setFirstArrival(isFirstArrival)
    }
}