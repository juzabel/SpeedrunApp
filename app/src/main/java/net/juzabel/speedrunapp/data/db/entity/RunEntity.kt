package net.juzabel.speedrunapp.data.db.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "run")
data class RunEntity(
        @PrimaryKey
        var id: String,
        @ColumnInfo(name = "game_id")
        var gameId: String?,
        @ColumnInfo(name = "video")
        var video: String?,
        @ColumnInfo(name = "player_name")
        var playerName: String?,
        @ColumnInfo(name = "time")
        var time: Long?

)