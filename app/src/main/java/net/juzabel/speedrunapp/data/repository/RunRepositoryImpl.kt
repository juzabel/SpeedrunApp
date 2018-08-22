package net.juzabel.speedrunapp.data.repository

import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction
import net.juzabel.speedrunapp.data.mapper.GameMapper
import net.juzabel.speedrunapp.data.mapper.RunMapper
import net.juzabel.speedrunapp.data.repository.datafactory.GameDataFactory
import net.juzabel.speedrunapp.data.repository.datafactory.RunDataFactory
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.domain.repository.RunRepository
import javax.inject.Inject

class RunRepositoryImpl @Inject constructor(private val runMapper: RunMapper,
                                            private val gameMapper: GameMapper,
                                            private val dataFactory: RunDataFactory,
                                            private val gameDataFactory: GameDataFactory) : RunRepository {
    override fun getRunByGameId(gameId: String): Observable<Pair<Run, Game>> {

        val dbMaybeRun: Maybe<Pair<Run, Game>> = dataFactory.createDBDataSource().getRunByGameId(gameId)
                .map { runMapper.toRun(it) }
                .zipWith( gameDataFactory.createDBDataSource().getGameById(gameId)
                        .map { gameMapper.toGame(it) }, BiFunction { run, game ->
                    Pair(run, game)
                })


        val nwMaybeRun: Maybe<Pair<Run, Game>> = dataFactory.createNetworkDataSource().getRunByGameId(gameId)
                .flatMap { run ->
                    dataFactory.createDBDataSource().delete(run)
                            .andThen(dataFactory.createDBDataSource().insert(run))
                            .andThen(Maybe.just(run))
                }
                .map { runMapper.toRun(it) }
                .zipWith( gameDataFactory.createDBDataSource().getGameById(gameId)
                        .map { gameMapper.toGame(it) }, BiFunction { run, game ->
                    Pair(run, game)
                })

        return Observable.mergeDelayError(dbMaybeRun.toObservable(), nwMaybeRun.toObservable())
    }
}