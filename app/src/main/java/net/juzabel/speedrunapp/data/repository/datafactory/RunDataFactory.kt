package net.juzabel.speedrunapp.data.repository.datafactory

import dagger.Lazy
import net.juzabel.speedrunapp.data.db.DBAdapter
import net.juzabel.speedrunapp.data.network.RestService
import net.juzabel.speedrunapp.data.repository.datasource.RunDBDataSource
import net.juzabel.speedrunapp.data.repository.datasource.RunNetworkDataSource
import javax.inject.Inject

class RunDataFactory @Inject constructor(private val dbAdapter: Lazy<DBAdapter>, private val restService: Lazy<RestService>) {

    fun createNetworkDataSource(): RunNetworkDataSource = RunNetworkDataSource(restService)

    fun createDBDataSource(): RunDBDataSource = RunDBDataSource(dbAdapter)

}