package net.juzabel.speedrunapp.data.repository.datasource

import io.reactivex.Observable
import io.reactivex.Single
import net.juzabel.speedrunapp.data.db.entity.GameEntity
import net.juzabel.speedrunapp.data.network.RestService
import javax.inject.Inject

class GameNetworkDataSource @Inject constructor(private val restService: RestService) : GameDataSource{
    override fun allGames(): Single<List<GameEntity>>
        = restService.getGamesList()
            .flatMap { Observable.fromIterable(it.data).map {GameEntity(it.id, it.names?.international, it.assets?.coverLarge?.uri) }
            .toList()
    }
}