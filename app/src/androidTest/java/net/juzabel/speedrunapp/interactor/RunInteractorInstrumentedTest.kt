package net.juzabel.speedrunapp.interactor

import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.TestObserver
import io.reactivex.schedulers.Schedulers
import junit.framework.TestCase.assertTrue
import net.juzabel.speedrunapp.FakeDataProvider
import net.juzabel.speedrunapp.base.BaseInstrumentedTest
import net.juzabel.speedrunapp.data.mapper.GameMapper
import net.juzabel.speedrunapp.data.mapper.RunMapper
import net.juzabel.speedrunapp.data.network.RestService
import net.juzabel.speedrunapp.data.repository.GameRepositoryImpl
import net.juzabel.speedrunapp.data.repository.RunRepositoryImpl
import net.juzabel.speedrunapp.data.repository.datafactory.GameDataFactory
import net.juzabel.speedrunapp.data.repository.datafactory.RunDataFactory
import net.juzabel.speedrunapp.domain.interactor.RunInteractor
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.util.concurrent.TimeUnit

class RunInteractorInstrumentedTest : BaseInstrumentedTest() {

    private lateinit var runMapper: RunMapper

    private lateinit var gameMapper: GameMapper

    private lateinit var runDataFactory: RunDataFactory

    private lateinit var gameDataFactory: GameDataFactory

    private lateinit var gameRepositoryImpl: GameRepositoryImpl

    private lateinit var runRepositoryImpl: RunRepositoryImpl

    private lateinit var runInteractor: RunInteractor

    @Before
    override fun setup() {
        super.setup()

        runMapper = RunMapper()

        gameMapper = GameMapper()

        runDataFactory = RunDataFactory(dbAdapter, RestService(apiService))

        gameDataFactory = GameDataFactory(dbAdapter, RestService(apiService))

        gameRepositoryImpl = GameRepositoryImpl(gameMapper, gameDataFactory)

        runRepositoryImpl = RunRepositoryImpl(runMapper, gameMapper, runDataFactory, gameDataFactory)

        runInteractor = RunInteractor(runRepositoryImpl)
    }

    @Test
    fun testRetrieveRunUser() {
        whenever(dbAdapter.getGameById(GAME_ID_USER)).thenReturn(Maybe.just(FakeDataProvider.getGameEntityWithGameId(GAME_ID_USER)))
        whenever(dbAdapter.getRunByGameId(GAME_ID_USER)).thenReturn(Maybe.empty())
        //Get first run
        var mockResponse = MockResponse().setResponseCode(200)
                .setBody(getJson("json/api/v1/runs/runs_1.json"))
        mockServer.enqueue(mockResponse)
        mockResponse = MockResponse().setResponseCode(200)
                .setBody(getJson("json/api/v1/users/user_1.json"))
        mockServer.enqueue(mockResponse)

        var testObserver: TestObserver<Pair<Run, Game>> = TestObserver()

        runInteractor.buildCaseObservable(GAME_ID_USER)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeWith(testObserver)

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        assertTrue((testObserver.events[0][0] as Pair<Run, Game>).first.gameId.equals(GAME_ID_USER))
        assertTrue((testObserver.events[0][0] as Pair<Run, Game>).second.id.equals(GAME_ID_USER))
        assertTrue((testObserver.events[0][0] as Pair<Run, Game>).first.playerName.equals(USER_NAME))
    }

    @Test
    fun testRetrieveRunGuest() {
        whenever(dbAdapter.getGameById(GAME_ID_GUEST)).thenReturn(Maybe.just(FakeDataProvider.getGameEntityWithGameId(GAME_ID_GUEST)))
        whenever(dbAdapter.getRunByGameId(GAME_ID_GUEST)).thenReturn(Maybe.empty())

        //Get first run
        var mockResponse = MockResponse().setResponseCode(200)
                .setBody(getJson("json/api/v1/runs/runs_2.json"))
        mockServer.enqueue(mockResponse)

        var testObserver: TestObserver<Pair<Run, Game>> = TestObserver()

        runInteractor.buildCaseObservable(GAME_ID_GUEST)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeWith(testObserver)

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        assertTrue((testObserver.events[0][0] as Pair<Run, Game>).first.gameId.equals(GAME_ID_GUEST))
        assertTrue((testObserver.events[0][0] as Pair<Run, Game>).second.id.equals(GAME_ID_GUEST))
        assertTrue((testObserver.events[0][0] as Pair<Run, Game>).first.playerName.equals(GUEST_NAME))
    }

    @Test
    fun testRetrieveRunError() {
        whenever(dbAdapter.getGameById(GAME_ID_GUEST)).thenReturn(Maybe.just(FakeDataProvider.getGameEntityWithGameId(GAME_ID_GUEST)))
        whenever(dbAdapter.getRunByGameId(GAME_ID_GUEST)).thenReturn(Maybe.empty())

        //Get first run
        var mockResponse = MockResponse().setResponseCode(500)
        mockServer.enqueue(mockResponse)

        var testObserver: TestObserver<Pair<Run, Game>> = TestObserver()

        runInteractor.buildCaseObservable(GAME_ID_GUEST)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread(), true)
                .subscribeWith(testObserver)

        testObserver.awaitTerminalEvent(2, TimeUnit.SECONDS)

        testObserver.assertError(HttpException::class.java)
        testObserver.assertNotComplete()
    }

    companion object {
        const val GAME_ID_USER = "1"
        const val GAME_ID_GUEST = "2"
        const val GUEST_NAME = "Player"
        const val USER_NAME = "User"
    }
}