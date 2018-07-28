package net.juzabel.speedrunapp.domain.interactor

import dagger.Lazy
import io.reactivex.Observable
import net.juzabel.speedrunapp.domain.interactor.base.UseCaseObservable
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.repository.GameRepository
import javax.inject.Inject

class GameInteractor @Inject constructor(private val gameRepository: Lazy<GameRepository>) : UseCaseObservable<List<Game>, Void>(){

    override fun buildCaseObservable(params: Void?): Observable<List<Game>> = gameRepository.get().getAll()

}