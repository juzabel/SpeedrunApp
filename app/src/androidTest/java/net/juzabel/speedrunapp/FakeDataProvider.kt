package net.juzabel.speedrunapp

import net.juzabel.speedrunapp.data.db.entity.GameEntity
import net.juzabel.speedrunapp.data.db.entity.RunEntity
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run

object FakeDataProvider {

    fun getListGameEntity(numGames: Int): List<GameEntity> {

        val gameList: ArrayList<GameEntity> = ArrayList()
        for (i in 0 until numGames) {
            var gameEntity = GameEntity(numGames.toString(), numGames.toString(), numGames.toString())
            gameList.add(gameEntity)
        }

        return gameList
    }

    fun getListGame(numGames: Int): List<Game> {

        val gameList: ArrayList<Game> = ArrayList()
        for (i in 0 until numGames) {
            var game = Game(numGames.toString(), numGames.toString(), numGames.toString())
            gameList.add(game)
        }

        return gameList
    }

    fun getRunWithGameId(gameId: String): Run = Run("", gameId, "", "", 0L)

    fun getGameWithGameId(gameId: String): Game = Game(gameId, gameId, "")
    fun getGameEntityWithGameId(gameId: String): GameEntity = GameEntity(gameId, gameId, "")

    fun getRunWithId(id: String, gameId: String, playerName: String): RunEntity = RunEntity(id, gameId, id, playerName, 0L)
}