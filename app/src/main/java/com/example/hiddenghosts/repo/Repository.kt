package com.example.hiddenghosts.repo

import com.example.hiddenghosts.data.LevelInfo

interface Repository {
    suspend fun getLevel(): Int
    suspend fun saveLevel(level: Int)
    fun getDataForLevel(level: Int): LevelInfo
}