package net.juzabel.speedrunapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.GameEntity

@Dao
interface GameDao {
    @Query("SELECT * FROM game")
    fun getAll(): Single<List<GameEntity>>

    @Query("SELECT * FROM game WHERE id=:id")
    fun getById(id: String): Single<GameEntity>

    @Insert
    fun insert(gameEntity: GameEntity)

    @Insert
    fun insertAll(gameEntityList: List<GameEntity>)

    @Delete
    fun delete(gameEntity: GameEntity)

    @Query("DELETE FROM game")
    fun deleteAll()
}