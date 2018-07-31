package net.juzabel.speedrunapp.data.network

import com.google.gson.Gson
import io.reactivex.Maybe
import io.reactivex.Single
import net.juzabel.speedrunapp.BuildConfig
import net.juzabel.speedrunapp.data.network.entity.Data
import net.juzabel.speedrunapp.data.network.entity.game.Game
import net.juzabel.speedrunapp.data.network.entity.run.Run
import net.juzabel.speedrunapp.data.network.entity.user.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RestService @Inject constructor(val api: Api){

    fun getGamesList(): Single<Data<List<Game>>> = api.getGamesList()

    fun getRunsList(gameId: String): Maybe<Data<List<Run>>> = api.getRunsList(gameId)

    fun getUser(id: String): Single<Data<User>> = api.getUser(id)
}