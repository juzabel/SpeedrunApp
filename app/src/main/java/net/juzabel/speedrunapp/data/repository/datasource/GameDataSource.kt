package net.juzabel.speedrunapp.data.repository.datasource

import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.GameEntity

interface GameDataSource {
    fun allGames(): Single<List<GameEntity>>
}