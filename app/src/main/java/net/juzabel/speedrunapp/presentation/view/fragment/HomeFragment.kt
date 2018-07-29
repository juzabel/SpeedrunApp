package net.juzabel.speedrunapp.presentation.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.*
import net.juzabel.speedrunapp.R
import net.juzabel.speedrunapp.di.modules.FragmentModule
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.presentation.base.extensions.FragmentExtensions.getActivityComponent
import net.juzabel.speedrunapp.presentation.contract.HomeContract
import net.juzabel.speedrunapp.presentation.presenter.HomePresenter
import net.juzabel.speedrunapp.presentation.util.ImageUtil
import net.juzabel.speedrunapp.presentation.view.adapter.GamesAdapter
import javax.inject.Inject

class HomeFragment : Fragment(), HomeContract.View{
    @Inject
    lateinit var homePresenter: HomePresenter

    @Inject
    lateinit var imageUtil: ImageUtil

    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getActivityComponent().plusFragmentComponent(FragmentModule()).inject(this)
        homePresenter.create()
        homePresenter.view = this
        homePresenter.getGamesList()

        viewManager = LinearLayoutManager(context)

        rvGameList.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }
    }

    override fun onGamesRetrieved(listGames: List<Game>) {
        rvGameList.adapter = GamesAdapter(listGames, imageUtil, homePresenter)
    }

    override fun onStop() {
        super.onStop()
        homePresenter.destroy()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}
