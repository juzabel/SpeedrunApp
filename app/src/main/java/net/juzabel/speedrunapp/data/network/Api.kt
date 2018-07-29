package net.juzabel.speedrunapp.data.network

import io.reactivex.Single
import net.juzabel.speedrunapp.data.network.entity.Data
import net.juzabel.speedrunapp.data.network.entity.game.Game
import net.juzabel.speedrunapp.data.network.entity.run.Run
import net.juzabel.speedrunapp.data.network.entity.user.User
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("api/v1/games")
    fun getGamesList(): Single<Data<List<Game>>>

    @GET("api/v1/runs")
    fun getRunsList(@Query("game") gameId: String): Single<Data<List<Run>>>

    @GET("api/v1/users/{id}")
    fun getUser(@Path("id") id: String): Single<Data<User>>
}