package net.juzabel.speedrunapp.domain.interactor

import io.reactivex.Observable
import net.juzabel.speedrunapp.domain.interactor.base.UseCase
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.repository.GameRepository
import javax.inject.Inject

class GameInteractor @Inject constructor(private val gameRepository: GameRepository) : UseCase<Observable<List<Game>>, Void>(){

    override fun buildCaseObservable(params: Void?): Observable<List<Game>> = gameRepository.getAll()

}