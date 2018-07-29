package net.juzabel.speedrunapp.di.modules

import dagger.Module
import dagger.Provides
import net.juzabel.speedrunapp.data.repository.GameRepositoryImpl
import net.juzabel.speedrunapp.data.repository.RunRepositoryImpl
import net.juzabel.speedrunapp.domain.repository.GameRepository
import net.juzabel.speedrunapp.domain.repository.RunRepository

@Module
class FragmentModule {
    @Provides
    fun provideGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository = gameRepositoryImpl

    @Provides
    fun provideRunRepository(runRepositoryImpl: RunRepositoryImpl): RunRepository = runRepositoryImpl
}