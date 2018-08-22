package net.juzabel.speedrunapp.data.repository.datafactory

import net.juzabel.speedrunapp.data.db.DBAdapter
import net.juzabel.speedrunapp.data.network.RestService
import net.juzabel.speedrunapp.data.repository.datasource.GameDBDataSource
import net.juzabel.speedrunapp.data.repository.datasource.GameNetworkDataSource
import javax.inject.Inject

class GameDataFactory @Inject constructor(private val dbAdapter: DBAdapter, private val restService: RestService) {

    fun createNetworkDataSource(): GameNetworkDataSource = GameNetworkDataSource(restService)

    fun createDBDataSource(): GameDBDataSource = GameDBDataSource(dbAdapter)

}