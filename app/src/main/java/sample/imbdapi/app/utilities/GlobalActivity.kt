package sample.imbdapi.app.utilities

import android.app.Application
import android.content.Context
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import sample.imbdapi.app.model.GeneralRepository
import sample.imbdapi.app.model.MovieRepository
import sample.imbdapi.app.model.db.FilmDatabse
import sample.imbdapi.app.model.preferences.SharedPrefProvider
import sample.imbdapi.app.model.remoteData.ApiClient
import sample.imbdapi.app.model.remoteData.ApiInterface
import sample.imbdapi.app.model.remoteData.Requests
import sample.imbdapi.app.ui.home.HomeViewModelFactory
import sample.imbdapi.app.ui.splash.SplashViewModelFactory
import sample.imbdapi.app.ui.movieDetail.MovieDetailViewModelFactory

class GlobalActivity : Application(), KodeinAware {

    companion object {
        private var instance: GlobalActivity? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@GlobalActivity))

//        utils
        bind() from singleton { SharedPrefProvider() }
        bind() from singleton { FilmDatabse(instance()) }

//        repositories
        bind() from singleton { GeneralRepository(instance()) }
        bind() from singleton { MovieRepository(instance(), instance(), instance()) }

//        factories
        bind() from provider { SplashViewModelFactory(instance()) }
        bind() from provider { HomeViewModelFactory(instance()) }
        bind() from provider { MovieDetailViewModelFactory(instance()) }

//        webservice
        bind() from singleton { ApiClient() }
        bind() from singleton { ApiInterface(instance()) }
        bind() from singleton { Requests(instance()) }

    }
}