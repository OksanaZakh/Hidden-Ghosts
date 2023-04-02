package com.example.hiddenghosts.sourse

import com.example.hiddenghosts.data.LevelInfo

interface LevelDataProvider {
    fun getDataForLevel(level: Int): LevelInfo
}
