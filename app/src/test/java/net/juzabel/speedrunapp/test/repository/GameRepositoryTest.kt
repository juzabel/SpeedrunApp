package net.juzabel.speedrunapp.test.repository

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import net.juzabel.speedrunapp.data.db.entity.GameEntity
import net.juzabel.speedrunapp.data.mapper.GameMapper
import net.juzabel.speedrunapp.data.repository.GameRepositoryImpl
import net.juzabel.speedrunapp.data.repository.datafactory.GameDataFactory
import net.juzabel.speedrunapp.data.repository.datasource.GameDBDataSource
import net.juzabel.speedrunapp.data.repository.datasource.GameNetworkDataSource
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.test.FakeDataProvider
import net.juzabel.speedrunapp.test.base.BaseTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GameRepositoryTest : BaseTest() {

    @Mock
    private lateinit var gameDBDataSource: GameDBDataSource

    @Mock
    private lateinit var gameDataFactory: GameDataFactory

    @Mock
    private lateinit var gameNetworkDataSource: GameNetworkDataSource

    @Mock
    private lateinit var gameMapper: GameMapper

    private lateinit var gameRepositoryImpl: GameRepositoryImpl

    private lateinit var gameListFromDB: List<GameEntity>

    private lateinit var gameListFromNW: List<GameEntity>

    private lateinit var exception: Exception

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        exception = Exception()

        whenever(gameDataFactory.createDBDataSource()).thenReturn(gameDBDataSource)
        whenever(gameDataFactory.createNetworkDataSource()).thenReturn(gameNetworkDataSource)
        whenever(gameMapper.toGame(any())).thenCallRealMethod()
        whenever(gameDBDataSource.deleteAll()).thenReturn(Completable.complete())
        whenever(gameDBDataSource.insertAll(any())).thenReturn(Completable.complete())

        gameRepositoryImpl = GameRepositoryImpl(Lazy { gameMapper }, Lazy { gameDataFactory })
    }

    @Test
    fun testGetAllGames() {
        gameListFromDB = FakeDataProvider.getListGameEntity(NUM_GAMES_DB)
        gameListFromNW = FakeDataProvider.getListGameEntity(NUM_GAMES_NW)

        whenever(gameDBDataSource.allGames()).thenReturn(Single.just(gameListFromDB))
        whenever(gameNetworkDataSource.allGames()).thenReturn(Single.just(gameListFromNW))

        val testObserver: TestObserver<List<Game>> = gameRepositoryImpl.getAll().test()
        testObserver.assertNoErrors()
        testObserver.assertComplete()

        val gameListDB: List<Game> = testObserver.events[0][0] as List<Game>
        val gameListNW: List<Game> = testObserver.events[0][1] as List<Game>

        Assert.assertTrue(gameListDB.size == NUM_GAMES_DB)
        Assert.assertTrue(gameListNW.size == NUM_GAMES_NW)
    }

    @Test
    fun testGetAllGamesErrorDB(){
        gameListFromDB = FakeDataProvider.getListGameEntity(NUM_GAMES_DB)

        whenever(gameDBDataSource.allGames()).thenReturn(Single.just(gameListFromDB))
        whenever(gameNetworkDataSource.allGames()).thenReturn(Single.error(exception))

        val testObserver: TestObserver<List<Game>> = gameRepositoryImpl.getAll().test()
        testObserver.assertError(exception)
        testObserver.assertNotComplete()

        val gameListBD: List<Game> = testObserver.events[0][0] as List<Game>
        Assert.assertTrue(gameListBD.size == NUM_GAMES_DB)
    }

    @Test
    fun testGetAllGamesErrorNW(){
        gameListFromNW = FakeDataProvider.getListGameEntity(NUM_GAMES_NW)

        whenever(gameDBDataSource.allGames()).thenReturn(Single.error(exception))
        whenever(gameNetworkDataSource.allGames()).thenReturn(Single.just(gameListFromNW))

        val testObserver: TestObserver<List<Game>> = gameRepositoryImpl.getAll().test()
        testObserver.assertError(exception)
        testObserver.assertNotComplete()

        val gameListNW: List<Game> = testObserver.events[0][0] as List<Game>
        Assert.assertTrue(gameListNW.size == NUM_GAMES_NW)
    }

    companion object {
        const val NUM_GAMES_DB = 4
        const val NUM_GAMES_NW = 6
    }

}