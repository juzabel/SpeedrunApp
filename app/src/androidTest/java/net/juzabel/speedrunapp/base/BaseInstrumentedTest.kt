package net.juzabel.speedrunapp.base

import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Completable
import net.juzabel.speedrunapp.data.db.DBAdapter
import net.juzabel.speedrunapp.data.network.Api
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(AndroidJUnit4::class)
abstract class BaseInstrumentedTest {

    @Mock
    protected lateinit var dbAdapter: DBAdapter

    protected lateinit var mockServer: MockWebServer

    protected lateinit var apiService: Api

    @Before
    open fun setup() {

        MockitoAnnotations.initMocks(this)

        whenever(dbAdapter.deleteAll()).thenReturn(Completable.complete())
        whenever(dbAdapter.deleteRun(any())).thenReturn(Completable.complete())
        whenever(dbAdapter.insertAll(any())).thenReturn(Completable.complete())
        whenever(dbAdapter.insertRun(any())).thenReturn(Completable.complete())

        mockServer = MockWebServer()
        mockServer.start(8080)

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit: Retrofit = Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://localhost:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()

        apiService = retrofit.create(Api::class.java)
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