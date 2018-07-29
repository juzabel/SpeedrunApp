package net.juzabel.speedrunapp.data.repository.datasource

import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.DBAdapter
import net.juzabel.speedrunapp.data.db.entity.GameEntity
import javax.inject.Inject

class GameDBDataSource @Inject constructor(private val dbAdapter: Lazy<DBAdapter>) : GameDataSource{
    override fun allGames(): Single<List<GameEntity>> = dbAdapter.get().allGames()

    fun insertAll(gameEntityList: List<GameEntity>): Completable
            = dbAdapter.get().insertAll(gameEntityList)

    fun deleteAll(): Completable
        = dbAdapter.get().deleteAll()

    fun getGameById(id: String): Single<GameEntity> = dbAdapter.get().getGameById(id)
}