package net.juzabel.speedrunapp.data.repository

import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.Single
import net.juzabel.speedrunapp.data.mapper.GameMapper
import net.juzabel.speedrunapp.data.repository.datafactory.GameDataFactory
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.repository.GameRepository
import javax.inject.Inject

class GameRepositoryImpl @Inject constructor(private val gameMapper: Lazy<GameMapper>, private val dataFactory: Lazy<GameDataFactory>) : GameRepository {
    override fun getAll(): Observable<List<Game>> {

        val dbSingle = dataFactory.get().createDBDataSource().allGames().flattenAsObservable { games -> games }
                .map { gameMapper.get().toGame(it) }
                .toList()

        val nwSingle = dataFactory.get().createNetworkDataSource()
                .allGames()
                .flatMap { gameList ->
                    dataFactory.get().createDBDataSource().deleteAll()
                            .andThen(dataFactory.get().createDBDataSource().insertAll(gameList))
                            .andThen(Single.just(gameList))
                }.flattenAsObservable { games -> games }
                .map { gameMapper.get().toGame(it) }
                .toList()

        return Observable.mergeDelayError(dbSingle.toObservable(), nwSingle.toObservable())
    }
}