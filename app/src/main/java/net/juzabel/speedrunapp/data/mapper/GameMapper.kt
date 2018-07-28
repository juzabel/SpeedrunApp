package net.juzabel.speedrunapp.data.mapper

import net.juzabel.speedrunapp.data.db.entity.GameEntity
import net.juzabel.speedrunapp.domain.model.Game
import javax.inject.Inject

class GameMapper @Inject constructor() {
    fun toGame(gameEntity: GameEntity): Game = Game(gameEntity.id, gameEntity.name, gameEntity.logo)

    fun toGameEntity(game: Game): GameEntity = GameEntity(game.id, game.name, game.logo)
}