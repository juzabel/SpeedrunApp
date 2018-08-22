package net.juzabel.speedrunapp.data.repository.datasource

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.DBAdapter
import net.juzabel.speedrunapp.data.db.entity.GameEntity
import javax.inject.Inject

class GameDBDataSource @Inject constructor(private val dbAdapter: DBAdapter) : GameDataSource{
    override fun allGames(): Single<List<GameEntity>> = dbAdapter.allGames()

    fun insertAll(gameEntityList: List<GameEntity>): Completable
            = dbAdapter.insertAll(gameEntityList)

    fun deleteAll(): Completable
        = dbAdapter.deleteAll()

    fun getGameById(id: String): Maybe<GameEntity> = dbAdapter.getGameById(id)
}