package net.juzabel.speedrunapp.test.interactor

import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import io.reactivex.Observable
import junit.framework.Assert.assertTrue
import net.juzabel.speedrunapp.domain.interactor.GameInteractor
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.repository.GameRepository
import net.juzabel.speedrunapp.test.FakeDataProvider
import net.juzabel.speedrunapp.test.base.BaseTest
import net.juzabel.speedrunapp.test.base.TestDisposableObserver
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GameInteractorTest: BaseTest() {

    @Mock
    private lateinit var gameRepository: GameRepository

    private lateinit var gameInteractor: GameInteractor

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        gameInteractor = GameInteractor(Lazy { gameRepository })
    }

    @Test
    fun testInteractorExecute() {
        whenever(gameRepository.getAll()).thenReturn(Observable.just(FakeDataProvider.getListGame(NUM_GAMES)))
        var testObserver: TestDisposableObserver<List<Game>> = TestDisposableObserver()
        gameInteractor.execute(testObserver, null)

        assertTrue(testObserver.completed)
        assertTrue(testObserver.listErrors.isEmpty())
        assertTrue(!testObserver.listValues.isEmpty())
    }

    @Test
    fun testInteractorErrorExecuted(){
        whenever(gameRepository.getAll()).thenReturn(Observable.error(Exception()))
        var testObserver: TestDisposableObserver<List<Game>> = TestDisposableObserver()
        gameInteractor.execute(testObserver, null)

        assertTrue(!testObserver.completed)
        assertTrue(!testObserver.listErrors.isEmpty())
        assertTrue(testObserver.listValues.isEmpty())
    }

    companion object {
        const val NUM_GAMES = 3
    }
}