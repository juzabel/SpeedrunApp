package net.juzabel.speedrunapp.data.network

import io.reactivex.Single
import net.juzabel.speedrunapp.data.network.entity.GameData
import retrofit2.http.GET

interface Api {
    @GET("api/v1/games")
    fun getGamesList(): Single<GameData>
}