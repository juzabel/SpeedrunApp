package net.juzabel.speedrunapp.data.repository.datasource

import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.RunEntity

interface RunDataSource {
    fun getRunByGameId(gameId: String): Single<RunEntity>?
}