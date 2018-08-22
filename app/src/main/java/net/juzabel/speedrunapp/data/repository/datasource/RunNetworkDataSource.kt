package net.juzabel.speedrunapp.data.repository.datasource

import io.reactivex.Maybe
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.RunEntity
import net.juzabel.speedrunapp.data.network.RestService
import javax.inject.Inject

class RunNetworkDataSource @Inject constructor(private val restService: RestService) : RunDataSource {
    override fun getRunByGameId(gameId: String): Maybe<RunEntity> {

        return restService.getRunsList(gameId).flatMap { run ->

            val id = run.data[0].players?.get(0)?.id

            if (id.isNullOrEmpty()) {
                Maybe.just(RunEntity(run.data[0].id, run.data[0].gameId, run.data[0].video?.links?.get(0)?.uri,
                        run.data[0].players?.get(0)?.name, run.data[0].time?.primaryT))

            } else if (!id.isNullOrEmpty()) {
                restService.getUser(id!!).flatMap {
                    Single.just(RunEntity(run.data[0].id, run.data[0].gameId, run.data[0].video?.links?.get(0)?.uri,
                            it.data.names.international, run.data[0].time?.primaryT))
                }.toMaybe()
            } else {
                Maybe.error(NoSuchElementException())
            }
        }
    }
}