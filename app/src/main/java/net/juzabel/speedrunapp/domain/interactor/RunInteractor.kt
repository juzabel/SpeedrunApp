package net.juzabel.speedrunapp.domain.interactor

import dagger.Lazy
import io.reactivex.Observable
import net.juzabel.speedrunapp.domain.interactor.base.UseCaseObservable
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.domain.repository.RunRepository
import javax.inject.Inject

class RunInteractor @Inject constructor(private val runRepository: Lazy<RunRepository>)
    : UseCaseObservable<Pair<Run, Game>, String>() {

    override fun buildCaseObservable(params: String?): Observable<Pair<Run, Game>> = if (params != null) runRepository.get().getRunByGameId(params) else Observable.error(IllegalStateException())
}