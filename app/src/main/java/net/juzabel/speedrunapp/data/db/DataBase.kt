package net.juzabel.speedrunapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.juzabel.speedrunapp.data.db.dao.GameDao
import net.juzabel.speedrunapp.data.db.entity.GameEntity

@Database(entities = arrayOf(GameEntity::class), version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}