package net.juzabel.speedrunapp.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_run.*
import net.juzabel.speedrunapp.R
import net.juzabel.speedrunapp.di.modules.FragmentModule
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.presentation.base.extensions.FragmentExtensions.getActivityComponent
import net.juzabel.speedrunapp.presentation.contract.RunContract
import net.juzabel.speedrunapp.presentation.presenter.RunPresenter
import net.juzabel.speedrunapp.presentation.util.ImageUtil
import javax.inject.Inject

class RunFragment : Fragment(), RunContract.View{

    @Inject
    lateinit var runPresenter: RunPresenter

    @Inject
    lateinit var imageUtil: ImageUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_run, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivityComponent().plusFragmentComponent(FragmentModule()).inject(this)
        runPresenter.create()
        runPresenter.view = this
        arguments?.getString(GAME_ID)?.let { runPresenter.loadRun(it) }
    }

    override fun onRunRetrieved(run: Run, game: Game) {
        tvPlayerName.text = run.playerName
        tvTime.text = run.time.toString()
        tvGameName.text = game.name
        btnVideo.setOnClickListener { run.video?.let { path -> runPresenter.loadVideo(path) } }
        game.logo?.let { imageUtil.load(it, imgRunLogo) }
    }

    companion object {

        const val GAME_ID = "GAME_ID"

        @JvmStatic
        fun newInstance(gameId: String) = RunFragment().apply {
                arguments = Bundle().apply {
                    putString(GAME_ID, gameId)
                }
            }

    }
}