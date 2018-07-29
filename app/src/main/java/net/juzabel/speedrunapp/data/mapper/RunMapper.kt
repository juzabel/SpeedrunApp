package net.juzabel.speedrunapp.data.mapper

import net.juzabel.speedrunapp.data.db.entity.RunEntity
import net.juzabel.speedrunapp.domain.model.Run
import javax.inject.Inject

class RunMapper @Inject constructor(){
    fun toRun(runEntity: RunEntity) = Run(runEntity.id, runEntity.gameId, runEntity.video, runEntity.playerName, runEntity.time)
}