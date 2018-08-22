package net.juzabel.speedrunapp.test.presenter

import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import io.reactivex.Observable
import net.juzabel.speedrunapp.domain.interactor.RunInteractor
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.presentation.Navigator
import net.juzabel.speedrunapp.presentation.contract.RunContract
import net.juzabel.speedrunapp.presentation.presenter.RunPresenter
import net.juzabel.speedrunapp.presentation.util.VideoUtil
import net.juzabel.speedrunapp.test.FakeDataProvider
import net.juzabel.speedrunapp.test.base.BaseTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RunPresenterTest : BaseTest() {
    @Mock
    private lateinit var runInteractor: RunInteractor

    @Mock
    private lateinit var navigator: Navigator

    @Mock
    private lateinit var videoUtil: VideoUtil

    @Mock
    private lateinit var view: RunContract.View

    private lateinit var runPresenter: RunPresenter

    private lateinit var run: Run

    private lateinit var game: Game

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        run = FakeDataProvider.getRunWithGameId(GAME_ID)
        game = FakeDataProvider.getGameWithGameId(GAME_ID)

        runPresenter = RunPresenter(Lazy { runInteractor }, Lazy { videoUtil }, Lazy { navigator })
        runPresenter.view = view
    }

    @Test
    fun testGetRun() {
        whenever(runInteractor.execute( eq(GAME_ID))).thenReturn(Observable.just(Pair(run, game)))
        runPresenter.loadRun(GAME_ID)
        verify(runInteractor, Mockito.times(1)).execute( eq(GAME_ID))
        verify(view, Mockito.times(1)).onRunRetrieved(run, game)
    }

    @Test
    fun testGetRunError() {
        whenever(runInteractor.execute( eq(GAME_ID))).thenReturn (Observable.error(Throwable(ERROR_MESSAGE)) )
        runPresenter.loadRun(GAME_ID)
        verify(runInteractor, Mockito.times(1)).execute( eq(GAME_ID))
        verify(navigator, Mockito.times(1)).showMessage(ERROR_MESSAGE)
    }

    @Test
    fun testLoadVideo() {
        runPresenter.loadVideo(VIDEO)
        verify(videoUtil, Mockito.times(1)).loadVideo(VIDEO)
    }

    companion object {
        const val ERROR_MESSAGE = "ERROR_MESSAGE"
        const val GAME_ID = "GAME_ID"
        const val VIDEO = "VIDEO"
    }
}