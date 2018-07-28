package net.juzabel.speedrunapp.presentation.contract

import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.presentation.base.BaseContract

interface HomeContract {
    interface View: BaseContract.BaseView {
        fun onGamesRetrieved(listGames: List<Game>)
    }
    interface Presenter: BaseContract.BasePresenter{
        fun getGamesList()
    }
}