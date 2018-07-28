package net.juzabel.speedrunapp.test

import net.juzabel.speedrunapp.data.db.entity.GameEntity
import net.juzabel.speedrunapp.domain.model.Game

object FakeDataProvider {

    fun getListGameEntity(numGames: Int): List<GameEntity> {

        val gameList: ArrayList<GameEntity> = ArrayList()
        for (i in 0 until numGames){
            var gameEntity = GameEntity(numGames.toString(), numGames.toString(), numGames.toString())
            gameList.add(gameEntity)
        }

        return gameList
    }

    fun getListGame(numGames: Int): List<Game> {

        val gameList: ArrayList<Game> = ArrayList()
        for (i in 0 until numGames){
            var game = Game(numGames.toString(), numGames.toString(), numGames.toString())
            gameList.add(game)
        }

        return gameList
    }
}