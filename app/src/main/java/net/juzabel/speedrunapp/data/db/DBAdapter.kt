package net.juzabel.speedrunapp.data.db

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.GameEntity
import javax.inject.Inject

class DBAdapter {

    val database: DataBase

    @Inject
    constructor(context: Lazy<Context>) {
        database = Room.databaseBuilder(context.get().applicationContext, DataBase::class.java, "database.db").build()
    }

    fun allGames(): Single<List<GameEntity>>
            = database.gameDao().getAll()

    fun insert(gameEntity: GameEntity): Completable
            = Completable.fromAction { database.gameDao().insert(gameEntity) }

    fun insertAll(gameEntityList: List<GameEntity>): Completable
        = Completable.fromAction { database.gameDao().insertAll(gameEntityList) }

    fun delete(gameEntity: GameEntity): Completable
            = Completable.fromAction { database.gameDao().delete(gameEntity) }

    fun deleteAll(): Completable
            = Completable.fromAction { database.gameDao().deleteAll() }
}