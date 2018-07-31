package net.juzabel.speedrunapp.interactor

import dagger.Lazy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import net.juzabel.speedrunapp.base.BaseInstrumentedTest
import net.juzabel.speedrunapp.data.mapper.GameMapper
import net.juzabel.speedrunapp.data.network.RestService
import net.juzabel.speedrunapp.data.repository.GameRepositoryImpl
import net.juzabel.speedrunapp.data.repository.datafactory.GameDataFactory
import net.juzabel.speedrunapp.domain.interactor.GameInteractor
import net.juzabel.speedrunapp.domain.model.Game
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class GameInteractorInstrumentedTest : BaseInstrumentedTest() {

    private lateinit var gameMapper: GameMapper

    private lateinit var gameDataFactory: GameDataFactory

    private lateinit var gameRepositoryImpl: GameRepositoryImpl

    private lateinit var gameInteractor: GameInteractor

    @Before
    override fun setup() {
        super.setup()

        gameMapper = GameMapper()

        gameDataFactory = GameDataFactory(Lazy { dbAdapter }, Lazy { RestService(apiService) })

        gameRepositoryImpl = GameRepositoryImpl(Lazy { gameMapper }, Lazy { gameDataFactory })

        gameInteractor = GameInteractor(Lazy { gameRepositoryImpl })
    }

    @Test
    fun testRetrieveGame() {

        // Test network OK
        var mockResponse = MockResponse().setResponseCode(200)
                .setBody(getJson("json/api/v1/games/games.json"))
        var testObserver = TestObserver<List<Game>>()

        mockServer.enqueue(mockResponse)

        gameInteractor.buildCaseObservable(null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeWith(testObserver)

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        assertTrue((testObserver.events[0][0] as List<Game>).size == 1)
        assertEquals(mockServer.takeRequest().path, "/api/v1/games")

        //Test NETWORK KO But got from DB
        mockResponse = MockResponse().setResponseCode(404)

        mockServer.enqueue(mockResponse)

        testObserver = TestObserver<List<Game>>()

        gameInteractor.buildCaseObservable(null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeWith(testObserver)

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        testObserver.assertError(HttpException::class.java)
        testObserver.assertNotComplete()
        assertTrue((testObserver.events[0][0] as List<Game>).size == 1)
    }
}