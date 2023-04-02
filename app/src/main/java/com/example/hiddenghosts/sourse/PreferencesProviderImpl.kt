package com.example.hiddenghosts.sourse

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import it.czerwinski.android.hilt.annotations.BoundTo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@BoundTo(
    supertype = PreferencesProvider::class,
    component = SingletonComponent::class
)
class PreferencesProviderImpl @Inject constructor(
    @ApplicationContext private val appContext: Context
) : PreferencesProvider {
    override suspend fun getLevelFromSharedPreferences() =
        appContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
            .getInt(KEY_NAME, 0)

    override suspend fun writeLevelToSharedPreferences(level: Int) {
        appContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit()
            .putInt(KEY_NAME, level).apply()
    }

    companion object {
        const val FILE_NAME = "hidden_ghost"
        const val KEY_NAME = "key_level"
    }
}
