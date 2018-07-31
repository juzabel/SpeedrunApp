package net.juzabel.speedrunapp.base

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import dagger.Lazy
import net.juzabel.speedrunapp.data.db.DBAdapter
import net.juzabel.speedrunapp.data.network.Api
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
abstract class BaseInstrumentedTest {

    protected lateinit var context: Context

    protected lateinit var dbAdapter: DBAdapter

    protected lateinit var mockServer: MockWebServer

    protected lateinit var apiService: Api

    @Before
    open fun setup() {
        mockServer = MockWebServer()
        mockServer.start(8080)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        context = InstrumentationRegistry.getTargetContext()

        apiService = retrofit.create(Api::class.java)

        dbAdapter = DBAdapter(Lazy { context })
    }

    /**
     * @see https://android.jlelse.eu/unit-test-api-calls-with-mockwebserver-d4fab11de847
     */
    fun getJson(path: String): String {
        val inputStream = this.javaClass.classLoader.getResourceAsStream(path)
        return String(inputStream.readBytes())
    }

    @After
    fun tearDown() {
        mockServer.shutdown()
    }
}