package net.juzabel.speedrunapp.presentation.presenter

import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import net.juzabel.speedrunapp.domain.interactor.RunInteractor
import net.juzabel.speedrunapp.presentation.Navigator
import net.juzabel.speedrunapp.presentation.base.DisposableManager
import net.juzabel.speedrunapp.presentation.contract.RunContract
import net.juzabel.speedrunapp.presentation.util.VideoUtil
import javax.inject.Inject

class RunPresenter @Inject constructor(private val runInteractor: Lazy<RunInteractor>,
                                       private val videoUtil: Lazy<VideoUtil>, private val navigator: Lazy<Navigator>) : DisposableManager(), RunContract.Presenter {

    lateinit var view: RunContract.View

    override fun loadRun(gameId: String) {
        runInteractor.get().execute(gameId).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeBy(
                        onError = { navigator.get().showMessage(it.localizedMessage) },
                        onNext = { view.onRunRetrieved(it.first, it.second) }
                )
                .addTo(compositeDisposable)
    }

    override fun loadVideo(path: String) {
        videoUtil.get().loadVideo(path)
    }

    override fun create() {
    }
}
