package net.juzabel.speedrunapp.di.modules

import dagger.Module
import dagger.Provides
import net.juzabel.speedrunapp.data.repository.GameRepositoryImpl
import net.juzabel.speedrunapp.domain.repository.GameRepository

@Module
class FragmentModule {
    @Provides
    fun provideGameRepository(gameRepositoryImpl: GameRepositoryImpl): GameRepository = gameRepositoryImpl
}