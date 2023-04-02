package com.example.hiddenghosts.sourse

import com.example.hiddenghosts.data.Grid
import com.example.hiddenghosts.data.LevelInfo
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(
    supertype = LevelDataProvider::class,
    component = SingletonComponent::class
)
class LevelDataProviderImpl @Inject constructor() : LevelDataProvider {
    override fun getDataForLevel(level: Int) =
        if (level in 0..levelsData.size) {
            when (level) {
                0, 1 -> levelsData.first()
                else -> levelsData[level - 1]
            }
        } else levelsData.last()

    private val levelsData = listOf(
        LevelInfo(1, Grid(4, 4), 4),
        LevelInfo(2, Grid(4, 5), 5),
        LevelInfo(3, Grid(4, 6), 6),
        LevelInfo(4, Grid(5, 6), 7),
        LevelInfo(5, Grid(5, 7), 8),
    )
}