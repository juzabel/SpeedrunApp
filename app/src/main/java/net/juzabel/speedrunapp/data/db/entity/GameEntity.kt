package net.juzabel.speedrunapp.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "game")
data class GameEntity(

        @PrimaryKey
        var id: String,

        @ColumnInfo(name="names")
        var name: String?,

        @ColumnInfo(name = "logo")
        var logo: String?
)