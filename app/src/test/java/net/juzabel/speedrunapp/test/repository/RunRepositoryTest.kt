package net.juzabel.speedrunapp.test.repository

import android.accounts.NetworkErrorException
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import net.juzabel.speedrunapp.data.db.entity.GameEntity
import net.juzabel.speedrunapp.data.db.entity.RunEntity
import net.juzabel.speedrunapp.data.mapper.GameMapper
import net.juzabel.speedrunapp.data.mapper.RunMapper
import net.juzabel.speedrunapp.data.repository.RunRepositoryImpl
import net.juzabel.speedrunapp.data.repository.datafactory.GameDataFactory
import net.juzabel.speedrunapp.data.repository.datafactory.RunDataFactory
import net.juzabel.speedrunapp.data.repository.datasource.GameDBDataSource
import net.juzabel.speedrunapp.data.repository.datasource.GameNetworkDataSource
import net.juzabel.speedrunapp.data.repository.datasource.RunDBDataSource
import net.juzabel.speedrunapp.data.repository.datasource.RunNetworkDataSource
import net.juzabel.speedrunapp.domain.exception.DBItemNotFoundException
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.test.FakeDataProvider
import net.juzabel.speedrunapp.test.base.BaseTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.lang.Exception

class RunRepositoryTest : BaseTest() {

    @Mock
    private lateinit var runDBDataSource: RunDBDataSource

    @Mock
    private lateinit var runDataFactory: RunDataFactory

    @Mock
    private lateinit var runNetworkDataSource: RunNetworkDataSource

    @Mock
    private lateinit var runMapper: RunMapper

    @Mock
    private lateinit var gameDBDataSource: GameDBDataSource

    @Mock
    private lateinit var gameDataFactory: GameDataFactory

    @Mock
    private lateinit var gameNetworkDataSource: GameNetworkDataSource

    @Mock
    private lateinit var gameMapper: GameMapper

    private lateinit var gameEntity: GameEntity

    private lateinit var runEntityFromDB: RunEntity

    private lateinit var runEntityFromNW: RunEntity

    private lateinit var exception: Exception

    private lateinit var runRepositoryImpl: RunRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        exception = Exception()

        whenever(runDataFactory.createDBDataSource()).thenReturn(runDBDataSource)
        whenever(runDataFactory.createNetworkDataSource()).thenReturn(runNetworkDataSource)
        whenever(runMapper.toRun(any())).thenCallRealMethod()
        whenever(gameDataFactory.createDBDataSource()).thenReturn(gameDBDataSource)
        whenever(gameDataFactory.createNetworkDataSource()).thenReturn(gameNetworkDataSource)
        whenever(gameMapper.toGame(any())).thenCallRealMethod()
        whenever(runDBDataSource.delete(any())).thenReturn(Completable.complete())
        whenever(runDBDataSource.insert(any())).thenReturn(Completable.complete())

        runRepositoryImpl = RunRepositoryImpl(Lazy { runMapper }, Lazy { gameMapper }, Lazy { runDataFactory }, Lazy { gameDataFactory })
    }

    @Test
    fun testGetRun() {
        runEntityFromDB = FakeDataProvider.getRunWithId(RUN_ID, RUN_GAME_ID, PLAYER_NAME_DB)
        runEntityFromNW = FakeDataProvider.getRunWithId(RUN_ID, RUN_GAME_ID, PLAYER_NAME_NW)
        gameEntity = FakeDataProvider.getListGameEntity(1)[0]

        whenever(gameDBDataSource.getGameById(any())).thenReturn(Single.just(gameEntity))
        whenever(runDBDataSource.getRunByGameId(RUN_GAME_ID)).thenReturn(Single.just(runEntityFromDB))
        whenever(runNetworkDataSource.getRunByGameId(RUN_GAME_ID)).thenReturn(Single.just(runEntityFromNW))

        val testObserver: TestObserver<Pair<Run, Game>> = runRepositoryImpl.getRunByGameId(RUN_GAME_ID).test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()

        val runAndGameDB: Pair<Run, Game> = testObserver.events[0][0] as Pair<Run, Game>
        val runAndGameNW: Pair<Run, Game> = testObserver.events[0][1] as Pair<Run, Game>

        Assert.assertTrue(runAndGameDB.first.gameId.equals(RUN_GAME_ID, true))
        Assert.assertTrue(runAndGameDB.first.playerName.equals(PLAYER_NAME_DB, true))
        Assert.assertTrue(runAndGameDB.second.id.equals(RUN_GAME_ID, true))

        Assert.assertTrue(runAndGameNW.first.gameId.equals(RUN_GAME_ID, true))
        Assert.assertTrue(runAndGameNW.first.playerName.equals(PLAYER_NAME_NW, true))
        Assert.assertTrue(runAndGameNW.second.id.equals(RUN_GAME_ID, true))
    }

    @Test
    fun testGetRunErrorDB(){
        runEntityFromNW = FakeDataProvider.getRunWithId(RUN_ID, RUN_GAME_ID, PLAYER_NAME_NW)
        gameEntity = FakeDataProvider.getListGameEntity(1)[0]

        whenever(runNetworkDataSource.getRunByGameId(RUN_GAME_ID)).thenReturn(Single.just(runEntityFromNW))
        whenever(gameDBDataSource.getGameById(any())).thenReturn(Single.just(gameEntity))
        whenever(runDBDataSource.getRunByGameId(RUN_GAME_ID)).thenReturn(Single.error(exception))

        val testObserver: TestObserver<Pair<Run, Game>> = runRepositoryImpl.getRunByGameId(RUN_GAME_ID).test()
        testObserver.assertError(DBItemNotFoundException::class.java)
        testObserver.assertNotComplete()

        val runAndGameNW: Pair<Run, Game> = testObserver.events[0][0] as Pair<Run, Game>

        Assert.assertTrue(runAndGameNW.first.gameId.equals(RUN_GAME_ID, true))
        Assert.assertTrue(runAndGameNW.first.playerName.equals(PLAYER_NAME_NW, true))
        Assert.assertTrue(runAndGameNW.second.id.equals(RUN_GAME_ID, true))
    }

    @Test
    fun testGetRunErrorNW(){
        runEntityFromDB = FakeDataProvider.getRunWithId(RUN_ID, RUN_GAME_ID, PLAYER_NAME_DB)
        gameEntity = FakeDataProvider.getListGameEntity(1)[0]

        whenever(runNetworkDataSource.getRunByGameId(RUN_GAME_ID)).thenReturn(Single.error(exception))
        whenever(gameDBDataSource.getGameById(any())).thenReturn(Single.just(gameEntity))
        whenever(runDBDataSource.getRunByGameId(RUN_GAME_ID)).thenReturn(Single.just(runEntityFromDB))

        val testObserver: TestObserver<Pair<Run, Game>> = runRepositoryImpl.getRunByGameId(RUN_GAME_ID).test()
        testObserver.assertError(exception)
        testObserver.assertNotComplete()

        val runAndGameNW: Pair<Run, Game> = testObserver.events[0][0] as Pair<Run, Game>

        Assert.assertTrue(runAndGameNW.first.gameId.equals(RUN_GAME_ID, true))
        Assert.assertTrue(runAndGameNW.first.playerName.equals(PLAYER_NAME_DB, true))
        Assert.assertTrue(runAndGameNW.second.id.equals(RUN_GAME_ID, true))
    }
    companion object {
        const val RUN_ID= "ID"
        const val RUN_GAME_ID = "1"
        const val PLAYER_NAME_DB = "PLAYER_NAME_DB"
        const val PLAYER_NAME_NW = "PLAYER_NAME_NW"
    }
}