package net.juzabel.speedrunapp.presentation.presenter

import dagger.Lazy
import io.reactivex.observers.DisposableObserver
import net.juzabel.speedrunapp.domain.interactor.GameInteractor
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.presentation.Navigator
import net.juzabel.speedrunapp.presentation.contract.HomeContract
import javax.inject.Inject

class HomePresenter @Inject constructor(private val gameInteractor: Lazy<GameInteractor>, private val navigator: Lazy<Navigator>) : HomeContract.Presenter,
        DisposableObserver<List<Game>>() {
    lateinit var view: HomeContract.View

    override fun getGamesList() {
        gameInteractor.get().execute(this, null)
    }

    override fun create() {
    }

    override fun destroy() {
        gameInteractor.get().clear()
    }

    override fun gameSelected(gameId: String) {
        navigator.get().loadRunFragment(gameId)
    }

    override fun onComplete() {
    }

    override fun onNext(gameList: List<Game>) {
        view.onGamesRetrieved(gameList)
    }

    override fun onError(e: Throwable) {
        navigator.get().showMessage(e.localizedMessage)
    }
}
