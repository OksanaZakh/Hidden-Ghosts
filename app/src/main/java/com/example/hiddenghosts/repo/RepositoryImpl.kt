package com.example.hiddenghosts.repo

import com.example.hiddenghosts.data.LevelInfo
import com.example.hiddenghosts.sourse.LevelDataProvider
import com.example.hiddenghosts.sourse.PreferencesProvider
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
@BoundTo(
    supertype = Repository::class,
    component = SingletonComponent::class
)
class RepositoryImpl @Inject constructor(
    private val preferencesProvider: PreferencesProvider,
    private val levelDataProvider: LevelDataProvider
): Repository {
    override suspend fun getLevel() = preferencesProvider.getLevelFromSharedPreferences()

    override suspend fun saveLevel(level: Int) = preferencesProvider.writeLevelToSharedPreferences(level)

    override fun getDataForLevel(level: Int): LevelInfo  = levelDataProvider.getDataForLevel(level)
}
