package net.juzabel.speedrunapp.presentation.contract

import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.presentation.base.BaseContract

interface RunContract {
    interface View: BaseContract.BaseView{
        fun onRunRetrieved(run: Run, game: Game)
    }

    interface Presenter: BaseContract.BasePresenter{
        fun loadRun(gameId: String)
        fun loadVideo(path: String)
    }
}