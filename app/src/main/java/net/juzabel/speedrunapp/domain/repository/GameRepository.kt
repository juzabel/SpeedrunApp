package net.juzabel.speedrunapp.domain.repository

import io.reactivex.Observable
import net.juzabel.speedrunapp.domain.model.Game

interface GameRepository {
    fun getAll(): Observable<List<Game>>
}