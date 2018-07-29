package net.juzabel.speedrunapp.data.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import net.juzabel.speedrunapp.data.db.dao.GameDao
import net.juzabel.speedrunapp.data.db.dao.RunDao
import net.juzabel.speedrunapp.data.db.entity.GameEntity
import net.juzabel.speedrunapp.data.db.entity.RunEntity

@Database(entities = arrayOf(GameEntity::class, RunEntity::class), version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun gameDao(): GameDao
    abstract fun runDao(): RunDao
}