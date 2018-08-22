package net.juzabel.speedrunapp.presentation.presenter

import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.juzabel.speedrunapp.domain.interactor.GameInteractor
import net.juzabel.speedrunapp.presentation.Navigator
import net.juzabel.speedrunapp.presentation.base.DisposableManager
import net.juzabel.speedrunapp.presentation.contract.HomeContract
import javax.inject.Inject

class HomePresenter @Inject constructor(private val gameInteractor: Lazy<GameInteractor>, private val navigator: Lazy<Navigator>) : DisposableManager(), HomeContract.Presenter {
    lateinit var view: HomeContract.View

    override fun getGamesList() {
        gameInteractor.get().execute(null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeBy(
                        onError = { navigator.get().showMessage(it.localizedMessage) },
                        onNext = { view.onGamesRetrieved(it) }
                )
                .addTo(compositeDisposable)
    }

    override fun create() {
    }

    override fun gameSelected(gameId: String) {
        navigator.get().loadRunFragment(gameId)
    }
}
