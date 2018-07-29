package net.juzabel.speedrunapp.test.interactor

import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import io.reactivex.Observable
import junit.framework.TestCase.assertTrue
import net.juzabel.speedrunapp.domain.interactor.RunInteractor
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.domain.repository.RunRepository
import net.juzabel.speedrunapp.test.FakeDataProvider
import net.juzabel.speedrunapp.test.base.BaseTest
import net.juzabel.speedrunapp.test.base.TestDisposableObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RunInteractorTest : BaseTest() {

    @Mock
    private lateinit var runRepository: RunRepository

    private lateinit var runInteractor: RunInteractor

    private lateinit var run: Run

    private lateinit var game: Game

    private lateinit var pairRunGame: Pair<Run, Game>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        run = FakeDataProvider.getRunWithGameId(GAME_ID)
        game = FakeDataProvider.getGameWithGameId(GAME_ID)

        pairRunGame = Pair(run, game)

        runInteractor = RunInteractor(Lazy { runRepository })
    }

    @Test
    fun testInteractorExecute() {
        whenever(runRepository.getRunByGameId(GAME_ID)).thenReturn(Observable.just(pairRunGame))
        var testObserver: TestDisposableObserver<Pair<Run, Game>> = TestDisposableObserver()
        runInteractor.execute(testObserver, GAME_ID)

        assertTrue(testObserver.completed)
        assertTrue(testObserver.listErrors.isEmpty())
        assertTrue(!testObserver.listValues.isEmpty())
    }

    @Test
    fun testInteractorErrorExecuted() {
        whenever(runRepository.getRunByGameId(GAME_ID)).thenReturn(Observable.error(Exception()))
        var testObserver: TestDisposableObserver<Pair<Run, Game>> = TestDisposableObserver()
        runInteractor.execute(testObserver, GAME_ID)

        assertTrue(!testObserver.completed)
        assertTrue(!testObserver.listErrors.isEmpty())
        assertTrue(testObserver.listValues.isEmpty())
    }

    companion object {
        const val GAME_ID = "GAME_ID"
    }
}