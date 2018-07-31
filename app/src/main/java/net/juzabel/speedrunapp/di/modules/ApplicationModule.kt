package net.juzabel.speedrunapp.di.modules

import android.content.Context
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import net.juzabel.speedrunapp.BuildConfig
import net.juzabel.speedrunapp.common.SpeedrunApplication
import net.juzabel.speedrunapp.data.network.Api
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class ApplicationModule(private val speedrunApplication: SpeedrunApplication) {

    @Provides
    fun provideSpeedrunApplication(): SpeedrunApplication = speedrunApplication

    @Provides
    fun provideContext(): Context = speedrunApplication

    @Provides
    fun provideApi(): Api {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder()

        if (BuildConfig.BUILD_TYPE.equals("debug", true)) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(interceptor)
        }

        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build()).build()
        return retrofit.create(Api::class.java)
    }
}