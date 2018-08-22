package net.juzabel.speedrunapp.data.repository.datasource

import io.reactivex.Completable
import io.reactivex.Maybe
import net.juzabel.speedrunapp.data.db.DBAdapter
import net.juzabel.speedrunapp.data.db.entity.RunEntity
import javax.inject.Inject

class RunDBDataSource @Inject constructor(private val dbAdapter: DBAdapter) : RunDataSource{
    override fun getRunByGameId(gameId: String): Maybe<RunEntity>
            = dbAdapter.getRunByGameId(gameId)

    fun insert(runEntity: RunEntity): Completable
            = dbAdapter.insertRun(runEntity)

    fun delete(runEntity: RunEntity): Completable
            = dbAdapter.deleteRun(runEntity)
}