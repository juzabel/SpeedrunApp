package net.juzabel.speedrunapp.data.repository

import io.reactivex.Observable
import io.reactivex.Single
import net.juzabel.speedrunapp.data.mapper.GameMapper
import net.juzabel.speedrunapp.data.repository.datafactory.GameDataFactory
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val gameMapper: GameMapper, private val dataFactory: GameDataFactory) : GameRepository {
    override fun getAll(): Observable<List<Game>> {

        val dbSingle = dataFactory.createDBDataSource().allGames().flattenAsObservable { games -> games }
                .map { gameMapper.toGame(it) }
                .toList()

        val nwSingle = dataFactory.createNetworkDataSource()
                .allGames()
                .flatMap { gameList ->
                    dataFactory.createDBDataSource().deleteAll()
                            .andThen(dataFactory.createDBDataSource().insertAll(gameList))
                            .andThen(Single.just(gameList))
                }.flattenAsObservable { games -> games }
                .map { gameMapper.toGame(it) }
                .toList()

        return Observable.mergeDelayError(dbSingle.toObservable(), nwSingle.toObservable())
    }
}