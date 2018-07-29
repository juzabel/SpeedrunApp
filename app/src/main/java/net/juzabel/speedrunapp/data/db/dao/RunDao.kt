package net.juzabel.speedrunapp.data.db.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.RunEntity

@Dao
interface RunDao {
    @Query("SELECT * FROM run WHERE id=:id")
    fun getRun(id: String): Single<RunEntity>

    @Query("SELECT * FROM run WHERE game_id=:id")
    fun getRunByGameId(id: String): Single<RunEntity>?

    @Insert
    fun insert(runEntity: RunEntity)

    @Delete
    fun delete(runEntity: RunEntity)
}