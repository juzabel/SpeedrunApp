package net.juzabel.speedrunapp.test.presenter

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import dagger.Lazy
import io.reactivex.Observable
import net.juzabel.speedrunapp.domain.interactor.GameInteractor
import net.juzabel.speedrunapp.presentation.Navigator
import net.juzabel.speedrunapp.presentation.contract.HomeContract
import net.juzabel.speedrunapp.presentation.presenter.HomePresenter
import net.juzabel.speedrunapp.test.base.BaseTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.*

class HomePresenterTest : BaseTest(){

    @Mock
    private lateinit var gameInteractor: GameInteractor

    @Mock
    private lateinit var navigator: Navigator

    @Mock
    private lateinit var view: HomeContract.View

    private lateinit var homePresenter: HomePresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        homePresenter = HomePresenter(Lazy { gameInteractor }, Lazy { navigator })
        homePresenter.view = view
    }

    @Test
    fun testGetGames() {
        whenever(gameInteractor.execute(eq(null))).thenReturn (Observable.just(Collections.emptyList()) )
        homePresenter.getGamesList()
        verify(gameInteractor, Mockito.times(1)).execute(eq(null))
        verify(view, Mockito.times(1)).onGamesRetrieved(any())
    }

    @Test
    fun testGetGamesError() {
        whenever(gameInteractor.execute(eq(null))).thenReturn(Observable.error(Exception(ERROR_MESSAGE)))
        homePresenter.getGamesList()
        verify(gameInteractor, Mockito.times(1)).execute(eq(null))
        verify(navigator, Mockito.times(1)).showMessage(ERROR_MESSAGE)
    }

    companion object {
        const val ERROR_MESSAGE = "ERROR_MESSAGE"
    }
}