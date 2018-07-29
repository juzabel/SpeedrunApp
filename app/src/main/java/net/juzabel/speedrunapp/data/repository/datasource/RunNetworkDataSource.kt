package net.juzabel.speedrunapp.data.repository.datasource

import dagger.Lazy
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.RunEntity
import net.juzabel.speedrunapp.data.network.RestService
import javax.inject.Inject

class RunNetworkDataSource @Inject constructor(private val restService: Lazy<RestService>) : RunDataSource {
    override fun getRunByGameId(gameId: String): Single<RunEntity>? {

        return restService.get().getRunsList(gameId).flatMap { run ->

            if (run.data[0].players?.get(0)?.id.isNullOrEmpty()) {
                Single.just(RunEntity(run.data[0].id, run.data[0].gameId, run.data[0].video?.links?.get(0)?.uri,
                        run.data[0].players?.get(0)?.name, run.data[0].time?.primaryT))

            } else if (!run.data[0].players?.get(0)?.id.isNullOrEmpty()) {
                run.data[0].players?.get(0)?.id?.let {
                    restService.get().getUser(it).flatMap {
                        Single.just(RunEntity(run.data[0].id, run.data[0].gameId, run.data[0].video?.links?.get(0)?.uri,
                                it.data.names.international, run.data[0].time?.primaryT))
                    }
                }

            } else {
                Single.error(NoSuchElementException())
            }
        }
    }
}