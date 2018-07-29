package net.juzabel.speedrunapp.presentation.presenter

import dagger.Lazy
import io.reactivex.observers.DisposableObserver
import net.juzabel.speedrunapp.domain.exception.DBItemNotFoundException
import net.juzabel.speedrunapp.domain.interactor.RunInteractor
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.presentation.Navigator
import net.juzabel.speedrunapp.presentation.contract.RunContract
import net.juzabel.speedrunapp.presentation.util.VideoUtil
import timber.log.Timber
import javax.inject.Inject

class RunPresenter @Inject constructor(private val runInteractor: Lazy<RunInteractor>,
                                       private val videoUtil: Lazy<VideoUtil>, private val navigator: Lazy<Navigator>)
    : RunContract.Presenter, DisposableObserver<Pair<Run, Game>>() {

    lateinit var view: RunContract.View

    override fun loadRun(gameId: String) {
        runInteractor.get().execute(this, gameId)
    }

    override fun loadVideo(path: String) {
        videoUtil.get().loadVideo(path)
    }

    override fun create() {
    }

    override fun destroy() {
        runInteractor.get().clear()
    }

    override fun onNext(pairRunGame: Pair<Run, Game>) {
        view.onRunRetrieved(pairRunGame.first, pairRunGame.second)
    }

    override fun onComplete() {
    }

    override fun onError(e: Throwable) {
        if(e !is DBItemNotFoundException) {
            Timber.e(e)
            navigator.get().showMessage(e.localizedMessage)
        }
    }
}
