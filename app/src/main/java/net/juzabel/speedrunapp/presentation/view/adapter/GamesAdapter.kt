package net.juzabel.speedrunapp.presentation.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_game.view.*
import net.juzabel.speedrunapp.R
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.presentation.presenter.HomePresenter
import net.juzabel.speedrunapp.presentation.util.ImageUtil

class GamesAdapter(private val gameList: List<Game>, private val imageUtil: ImageUtil, private val homePresenter: HomePresenter)
    : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view, imageUtil, homePresenter)
    }

    override fun getItemCount(): Int = gameList.size

    override fun onBindViewHolder(viewHolder: ViewHolder, pos: Int) {
        viewHolder.bindName(gameList[pos].name)
        viewHolder.bindImage(gameList[pos].logo)
        viewHolder.bindClick(gameList[pos].id)
    }

    class ViewHolder(view: View, private val imageUtil: ImageUtil, private val homePresenter: HomePresenter) : RecyclerView.ViewHolder(view) {

        fun bindName(name: String?) {
            itemView.tvName.text = name
        }

        fun bindImage(path: String?) {
            path?.let {
                imageUtil.load(it, itemView.imgLogo)
            }
        }
        fun bindClick(gameId: String){
            itemView.cvRootItem.setOnClickListener {
                homePresenter.gameSelected(gameId)
            }
        }
    }
}