package net.juzabel.speedrunapp.data.repository

import android.accounts.NetworkErrorException
import dagger.Lazy
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import net.juzabel.speedrunapp.data.mapper.GameMapper
import net.juzabel.speedrunapp.data.mapper.RunMapper
import net.juzabel.speedrunapp.data.repository.datafactory.GameDataFactory
import net.juzabel.speedrunapp.data.repository.datafactory.RunDataFactory
import net.juzabel.speedrunapp.domain.exception.DBItemNotFoundException
import net.juzabel.speedrunapp.domain.model.Game
import net.juzabel.speedrunapp.domain.model.Run
import net.juzabel.speedrunapp.domain.repository.RunRepository
import javax.inject.Inject

class RunRepositoryImpl @Inject constructor(private val runMapper: Lazy<RunMapper>,
                                            private val gameMapper: Lazy<GameMapper>,
                                            private val dataFactory: Lazy<RunDataFactory>,
                                            private val gameDataFactory: Lazy<GameDataFactory>) : RunRepository {
    override fun getRunByGameId(gameId: String): Observable<Pair<Run, Game>> {

        val dbMaybeRun: Maybe<Pair<Run, Game>> = dataFactory.get().createDBDataSource().getRunByGameId(gameId)
                .map { runMapper.get().toRun(it) }
                .zipWith( gameDataFactory.get().createDBDataSource().getGameById(gameId)
                        .map { gameMapper.get().toGame(it) }, BiFunction { run, game ->
                    Pair(run, game)
                })


        val nwMaybeRun: Maybe<Pair<Run, Game>> = dataFactory.get().createNetworkDataSource().getRunByGameId(gameId)
                .flatMap { run ->
                    dataFactory.get().createDBDataSource().delete(run)
                            .andThen(dataFactory.get().createDBDataSource().insert(run))
                            .andThen(Maybe.just(run))
                }
                .map { runMapper.get().toRun(it) }.zipWith( gameDataFactory.get().createDBDataSource().getGameById(gameId)
                        .map { gameMapper.get().toGame(it) }, BiFunction { run, game ->
                    Pair(run, game)
                })

        return Observable.mergeDelayError(dbMaybeRun.toObservable(), nwMaybeRun.toObservable())
    }
}