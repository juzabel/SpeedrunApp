package net.juzabel.speedrunapp.data.repository.datasource

import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.DBAdapter
import net.juzabel.speedrunapp.data.db.entity.RunEntity
import javax.inject.Inject

class RunDBDataSource @Inject constructor(private val dbAdapter: Lazy<DBAdapter>) : RunDataSource{
    override fun getRunByGameId(gameId: String): Maybe<RunEntity>
            = dbAdapter.get().getRunByGameId(gameId)

    fun insert(runEntity: RunEntity): Completable
            = dbAdapter.get().insertRun(runEntity)

    fun delete(runEntity: RunEntity): Completable
            = dbAdapter.get().deleteRun(runEntity)
}