package net.juzabel.speedrunapp.data.network.entity.run

import com.google.gson.annotations.SerializedName

data class Run(val id: String,
               @SerializedName("game") val gameId: String?,
               @SerializedName("videos") val video: Video?,
               val players: List<Player>?,
               @SerializedName("times") val time: Time?)