package net.juzabel.speedrunapp.data.network.entity.run

import com.google.gson.annotations.SerializedName

data class Time(val id: String,
                @SerializedName("primary_t") val primaryT: Long?,
                @SerializedName("realtime_t") val realtimeT: Long?)