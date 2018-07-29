package net.juzabel.speedrunapp.data.network

import com.google.gson.Gson
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

class RestService @Inject constructor(){

    private val api: Api

    init {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        if(BuildConfig.BUILD_TYPE.equals("debug", true)) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build()).build()
        api = retrofit.create(Api::class.java)
    }

    fun getGamesList(): Single<Data<List<Game>>> = api.getGamesList()

    fun getRunsList(gameId: String): Single<Data<List<Run>>> = api.getRunsList(gameId)

    fun getUser(id: String): Single<Data<User>> = api.getUser(id)
}