package net.juzabel.speedrunapp.data.db

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.GameEntity
import net.juzabel.speedrunapp.data.db.entity.RunEntity
import javax.inject.Inject

class DBAdapter {

    private val database: DataBase

    @Inject
    constructor(context: Lazy<Context>) {
        database = Room.databaseBuilder(context.get().applicationContext, DataBase::class.java, "database.db").build()
    }

    fun allGames(): Single<List<GameEntity>>
            = database.gameDao().getAll()

    fun getGameById(id: String): Maybe<GameEntity>
        = database.gameDao().getById(id)

    fun insertAll(gameEntityList: List<GameEntity>): Completable
        = Completable.fromAction { database.gameDao().insertAll(gameEntityList) }

    fun deleteAll(): Completable
            = Completable.fromAction { database.gameDao().deleteAll() }

    fun getRunByGameId(gameId: String): Maybe<RunEntity>
        = database.runDao().getRunByGameId(gameId)

    fun insertRun(runEntity: RunEntity): Completable
        = Completable.fromAction { database.runDao().insert(runEntity) }

    fun deleteRun(runEntity: RunEntity): Completable
        = Completable.fromAction { database.runDao().delete(runEntity) }
}