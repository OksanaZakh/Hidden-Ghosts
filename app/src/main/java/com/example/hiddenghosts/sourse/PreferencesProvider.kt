package com.example.hiddenghosts.sourse

interface PreferencesProvider {
    suspend fun getLevelFromSharedPreferences(): Int
    suspend fun writeLevelToSharedPreferences(level: Int)
}