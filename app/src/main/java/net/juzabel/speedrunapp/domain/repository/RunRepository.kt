package net.juzabel.speedrunapp.domain.repository

import io.reactivex.Observable
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run

interface RunRepository {
    fun getRunByGameId(gameId: String): Observable<Pair<Run, Game>>
}